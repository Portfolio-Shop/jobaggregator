package tech.portfolioshop.users.integration;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.jobaggregator.kafka.config.KafkaTopics;
import org.jobaggregator.kafka.payload.UserDeleted;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import tech.portfolioshop.users.UsersApplication;
import tech.portfolioshop.users.data.UserEntity;
import tech.portfolioshop.users.data.UserRepository;
import tech.portfolioshop.users.shared.UserDto;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = UsersApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.test.properties")
@EmbeddedKafka(
        partitions = 1,
        ports = 9092,
        zookeeperPort = 2181,
        topics = {KafkaTopics.USER_DELETED}
)
@DirtiesContext(
        classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD
)
public class ProfileDeleteFlowTest {

    private final MockMvc mockMvc;
    private final UserRepository userRepository;
    private Consumer<String, String> consumer;
    private final EmbeddedKafkaBroker embeddedKafkaBroker;
    private final Environment environment;


    @Autowired
    public ProfileDeleteFlowTest(
            MockMvc mockMvc,
            UserRepository userRepository,
            EmbeddedKafkaBroker embeddedKafkaBroker,
            Environment environment) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.embeddedKafkaBroker = embeddedKafkaBroker;
        this.environment = environment;
    }

    @BeforeEach
    public void setup() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("Test", "true", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        ConsumerFactory<String, String> cf = new org.springframework.kafka.core.DefaultKafkaConsumerFactory<>(consumerProps);
        consumer = cf.createConsumer();
        embeddedKafkaBroker.consumeFromAllEmbeddedTopics(consumer);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        consumer.close();
    }

    private UserDto getMockUser() {
        UserDto userDto = new UserDto();
        userDto.setName("test");
        userDto.setEmail("test@test.test");
        userDto.setPhone("1234567890");
        userDto.setPassword("test1234567890");
        return userDto;
    }

    private String saveMockUser() {
        UserDto userDto = getMockUser();
        UserEntity userEntity = new ModelMapper().map(userDto, UserEntity.class);
        userEntity.setEncryptedPassword("test1234567890");
        userEntity.setUserId("testUserId");
        userEntity.setStatus(true);
        userRepository.save(userEntity);
        return "Bearer " + Jwts.builder()
                .setSubject(userEntity.getUserId())
                .signWith(SignatureAlgorithm.HS256, environment.getProperty("jwt.secret"))
                .compact();
    }

    @Test
    @DisplayName("Delete the profile with token")
    public void deleteProfile() throws Exception {
        String token = saveMockUser();
        mockMvc.perform(delete("/api/v1/user/profile")
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNoContent());
        UserEntity userEntity = userRepository.findByEmail(getMockUser().getEmail());
        assert userEntity == null;
        ConsumerRecord<String, String> record = KafkaTestUtils.getSingleRecord(consumer, KafkaTopics.USER_DELETED);
        assert record != null;
        UserDeleted userDeleted = new UserDeleted().deserialize(record.value());
        assert userDeleted.getUserId().equals("testUserId");
    }

    @Test
    @DisplayName("Can't delete the profile without token")
    public void deleteProfileWithoutToken() throws Exception {
        saveMockUser();
        mockMvc.perform(delete("/api/v1/user/profile"))
                .andExpect(status().isBadRequest());
        UserEntity userEntity = userRepository.findByEmail(getMockUser().getEmail());
        assert userEntity != null;
        ConsumerRecords<String, String> records = KafkaTestUtils.getRecords(consumer);
        assert records.count() == 0;
    }
}
