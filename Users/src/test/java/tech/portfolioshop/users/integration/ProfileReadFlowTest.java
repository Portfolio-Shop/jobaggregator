package tech.portfolioshop.users.integration;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import tech.portfolioshop.users.UsersApplication;
import tech.portfolioshop.users.data.UserEntity;
import tech.portfolioshop.users.data.UserRepository;
import tech.portfolioshop.users.shared.UserDto;


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
        zookeeperPort = 2181
)
@DirtiesContext(
        classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD
)
public class ProfileReadFlowTest {

    private final MockMvc mockMvc;
    private final UserRepository userRepository;
    private final Environment environment;


    @Autowired
    public ProfileReadFlowTest(
            MockMvc mockMvc,
            UserRepository userRepository,
            Environment environment) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.environment = environment;
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
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
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Fail to fetch the profile with invalid token")
    public void getProfileWithInvalidToken() throws Exception {
        mockMvc.perform(get("/api/v1/user/profile")
                        .header(HttpHeaders.AUTHORIZATION, "invalid"))
                .andExpect(status().isUnauthorized());
    }
}
