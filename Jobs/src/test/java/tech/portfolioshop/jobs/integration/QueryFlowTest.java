package tech.portfolioshop.jobs.integration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jobaggregator.kafka.config.KafkaTopics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;
import tech.portfolioshop.jobs.JobsApplication;
import tech.portfolioshop.jobs.data.SearchEntity;
import tech.portfolioshop.jobs.data.SearchRepository;
import tech.portfolioshop.jobs.data.UserEntity;
import tech.portfolioshop.jobs.data.UserRepository;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = JobsApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.test.properties"
)
@EmbeddedKafka(
        partitions = 1,
        ports = 9092,
        zookeeperPort = 2181,
        topics = {KafkaTopics.JOB_SEARCH_TRIGGERED}
)
@DirtiesContext(
        classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD
)
public class QueryFlowTest {
    private final MockMvc mockMvc;
    private final UserRepository userRepository;
    private final SearchRepository searchRepository;
    private final Environment environment;
    private Consumer<String, String> consumer;
    private final EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    public QueryFlowTest(
            MockMvc mockMvc,
            UserRepository userRepository,
            SearchRepository searchRepository,
            Environment environment,
            EmbeddedKafkaBroker embeddedKafkaBroker) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.searchRepository = searchRepository;
        this.environment = environment;
        this.embeddedKafkaBroker = embeddedKafkaBroker;
    }

    record Auth(String userId, String token, UserEntity userEntity) { }
    private Auth getMockAuth(){
        String userId = "test-userid";
        String token = "Bearer " + Jwts.builder()
                .setSubject(userId)
                .signWith(SignatureAlgorithm.HS256, environment.getProperty("jwt.secret"))
                .compact();
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        return new Auth(userId, token, userEntity);
    }

    @BeforeEach
    public void setup() {
        Auth auth = getMockAuth();
        userRepository.save(auth.userEntity());

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("Test", "true", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        ConsumerFactory<String, String> cf = new org.springframework.kafka.core.DefaultKafkaConsumerFactory<>(consumerProps);
        consumer = cf.createConsumer();
        embeddedKafkaBroker.consumeFromAllEmbeddedTopics(consumer);
    }

    @AfterEach
    public void tearDown() {
        searchRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Query Without token")
    public void queryWithToken() throws Exception {
        mockMvc.perform(get("/api/v1/jobs?query=java&location=seoul"))
                .andExpect(status().isOk());
        ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, KafkaTopics.JOB_SEARCH_TRIGGERED);
        assert singleRecord != null;
        assert singleRecord.value().contains("java");
        assert singleRecord.value().contains("seoul");

        List<SearchEntity> searchEntities = (List<SearchEntity>) searchRepository.findAll();
        assert searchEntities.size() == 0;
    }

    @Test
    @DisplayName("Query With token")
    public void queryWithoutToken() throws Exception {
        Auth auth = getMockAuth();
        for (UserEntity u : userRepository.findAll()) {
            System.out.println(u.getId());
        }
        mockMvc.perform(get("/api/v1/jobs?query=java&location=seoul")
                .header(HttpHeaders.AUTHORIZATION, auth.token()))
                .andExpect(status().isOk());
        ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, KafkaTopics.JOB_SEARCH_TRIGGERED);
        assert singleRecord != null;
        assert singleRecord.value().contains("java");
        assert singleRecord.value().contains("seoul");

        List<SearchEntity> searchEntities = (List<SearchEntity>) searchRepository.findAll();
        assert searchEntities.size() == 1;
    }
}
