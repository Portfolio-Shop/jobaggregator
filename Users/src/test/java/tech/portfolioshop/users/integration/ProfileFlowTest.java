package tech.portfolioshop.users.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.jobaggregator.kafka.config.KafkaTopics;
import org.jobaggregator.kafka.payload.UserUpdated;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import tech.portfolioshop.users.UsersApplication;
import tech.portfolioshop.users.data.UserEntity;
import tech.portfolioshop.users.data.UserRepository;
import tech.portfolioshop.users.models.request.SignUpRequest;
import tech.portfolioshop.users.shared.UserDto;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
        topics = {KafkaTopics.USER_UPDATED, KafkaTopics.USER_DELETED, KafkaTopics.USER_CREATED}
)
public class ProfileFlowTest {

    private final MockMvc mockMvc;
    private final UserRepository userRepository;
    private Consumer<String, String> consumer;
    private final EmbeddedKafkaBroker embeddedKafkaBroker;


    @Autowired
    public ProfileFlowTest(
            MockMvc mockMvc,
            UserRepository userRepository,
            EmbeddedKafkaBroker embeddedKafkaBroker
    ) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.embeddedKafkaBroker = embeddedKafkaBroker;
    }

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("Test", "true", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        ConsumerFactory<String, String> cf = new org.springframework.kafka.core.DefaultKafkaConsumerFactory<>(consumerProps);
        consumer = cf.createConsumer();
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, KafkaTopics.USER_UPDATED);
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, KafkaTopics.USER_DELETED);
    }

    @AfterEach
    public void tearDown() {
        consumer.close();
        // Kafka events after each test is cleared using dirties context

    }

    private UserDto getMockUser() {
        UserDto userDto = new UserDto();
        userDto.setName("test");
        userDto.setEmail("test@test.test");
        userDto.setPhone("1234567890");
        userDto.setPassword("test1234567890");
        return userDto;
    }

    private String saveMockUser() throws Exception {
        UserDto userDto = getMockUser();
        SignUpRequest signUpRequest = new ModelMapper().map(userDto, SignUpRequest.class);
        String signUpRequestJson = new ObjectMapper().writeValueAsString(signUpRequest);
        return mockMvc.perform(post("/api/v1/user/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signUpRequestJson))
                .andReturn().getResponse().getHeader(HttpHeaders.AUTHORIZATION);
    }

    @Test
    @DisplayName("Get the profile using token")
    public void getProfile() throws Exception {
        String token = saveMockUser();
        UserDto mockUser = getMockUser();
        mockMvc.perform(get("/api/v1/user/profile")
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(mockUser.getName()))
                .andExpect(jsonPath("$.email").value(mockUser.getEmail()))
                .andExpect(jsonPath("$.phone").value(mockUser.getPhone()));
    }

    @Test
    @DisplayName("Fail to fetch the profile without token")
    public void getProfileWithoutToken() throws Exception {
        mockMvc.perform(get("/api/v1/user/profile"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Fail to fetch the profile with invalid token")
    public void getProfileWithInvalidToken() throws Exception {
        mockMvc.perform(get("/api/v1/user/profile")
                        .header(HttpHeaders.AUTHORIZATION, "invalid"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Update the profile with token")
    public void updateProfile() throws Exception {
        String token = saveMockUser();
        UserDto mockUser = getMockUser();
        mockUser.setName("test2");
        mockUser.setPhone("0987654321");
        String mockUserJson = new ObjectMapper().writeValueAsString(mockUser);
        mockMvc.perform(put("/api/v1/user/profile")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(mockUser.getName()))
                .andExpect(jsonPath("$.email").value(mockUser.getEmail()))
                .andExpect(jsonPath("$.phone").value(mockUser.getPhone()));

        ConsumerRecord<String, String> record = KafkaTestUtils.getSingleRecord(consumer, KafkaTopics.USER_UPDATED);
        assert record != null;
        UserUpdated userUpdated = new UserUpdated().deserialize(record.value());
        assert userUpdated.getName().equals(mockUser.getName());
        assert userUpdated.getPhone().equals(mockUser.getPhone());

        UserEntity userEntity = userRepository.findByEmail(mockUser.getEmail());
        assert userEntity != null;
        assert userEntity.getName().equals(mockUser.getName());
        assert userEntity.getPhone().equals(mockUser.getPhone());

        assert userEntity.getUserId().equals(userUpdated.getUserId());
    }

    @Test
    @DisplayName("Can't update the profile without token")
    public void updateProfileWithoutToken() throws Exception {
        saveMockUser();
        UserDto mockUser = getMockUser();
        mockUser.setName("test2");
        mockUser.setPhone("0987654321");
        String mockUserJson = new ObjectMapper().writeValueAsString(mockUser);
        mockMvc.perform(put("/api/v1/user/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockUserJson))
                .andExpect(status().isUnauthorized());
        UserDto pastUser = getMockUser();
        UserEntity userEntity = userRepository.findByEmail(pastUser.getEmail());
        assert userEntity != null;
        assert userEntity.getName().equals(pastUser.getName());
        assert userEntity.getPhone().equals(pastUser.getPhone());

        ConsumerRecords<String, String> records = KafkaTestUtils.getRecords(consumer);
        assert records.count() == 0;
    }

    @Test
    @DisplayName("Can't update the profile with invalid token")
    public void updateProfileWithInvalidToken() throws Exception {
        saveMockUser();
        UserDto mockUser = getMockUser();
        mockUser.setName("test2");
        mockUser.setPhone("0987654321");
        String mockUserJson = new ObjectMapper().writeValueAsString(mockUser);
        mockMvc.perform(put("/api/v1/user/profile")
                        .header(HttpHeaders.AUTHORIZATION, "invalid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockUserJson))
                .andExpect(status().isUnauthorized());
        UserDto pastUser = getMockUser();
        UserEntity userEntity = userRepository.findByEmail(pastUser.getEmail());
        assert userEntity != null;
        assert userEntity.getName().equals(pastUser.getName());
        assert userEntity.getPhone().equals(pastUser.getPhone());

        ConsumerRecords<String, String> records = KafkaTestUtils.getRecords(consumer);
        assert records.count() == 0;
    }

}
