package tech.portfolioshop.users.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import tech.portfolioshop.users.UsersApplication;
import tech.portfolioshop.users.data.UserEntity;
import tech.portfolioshop.users.data.UserRepository;
import tech.portfolioshop.users.models.request.SignInRequest;
import tech.portfolioshop.users.models.request.SignUpRequest;
import tech.portfolioshop.users.shared.UserDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
public class SigninFlowTest {

    private final MockMvc mockMvc;
    private final UserRepository userRepository;

    @Autowired
    public SigninFlowTest(MockMvc mockMvc, UserRepository userRepository) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setup(){
        userRepository.deleteAll();
    }

    private UserDto getMockUser() {
        UserDto userDto = new UserDto();
        userDto.setName("test");
        userDto.setEmail("test@test.com");
        userDto.setPhone("1234567890");
        userDto.setPassword("test1234567890");
        return userDto;
    }

    private void saveMockUser() throws Exception {
        UserDto userDto = getMockUser();
        SignUpRequest signUpRequest = new ModelMapper().map(userDto, SignUpRequest.class);
        mockMvc.perform(post("/api/v1/user/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signUpRequest)));
    }

    private void deleteMockUser() {
        UserEntity userEntity = userRepository.findByEmail(getMockUser().getEmail());
        userEntity.setStatus(false);
        userRepository.save(userEntity);
    }

    @Test
    @DisplayName("Signin flow test")
    public void signinFlowTest() throws Exception {
        saveMockUser();
        UserDto userDto = getMockUser();
        SignInRequest signinRequest = new ModelMapper().map(userDto, SignInRequest.class);
        System.out.println(new ObjectMapper().writeValueAsString(signinRequest));
        mockMvc.perform(post("/api/v1/user/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signinRequest))
                ).andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.AUTHORIZATION));
    }

    @Test
    @DisplayName("Signin flow test fails without registered user")
    public void signinFlowTestFailsWithoutRegisteredUser() throws Exception {
        UserDto userDto = getMockUser();
        SignInRequest signinRequest = new ModelMapper().map(userDto, SignInRequest.class);
        mockMvc.perform(post("/api/v1/user/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signinRequest))
                ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Signin flow test fails with wrong password")
    public void signinFlowTestFailsWithWrongPassword() throws Exception {
        saveMockUser();
        UserDto userDto = getMockUser();
        userDto.setPassword("wrongPassword");
        SignInRequest signinRequest = new ModelMapper().map(userDto, SignInRequest.class);
        mockMvc.perform(post("/api/v1/user/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signinRequest))
                ).andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Signin flow test fails with deleted user")
    public void signinFlowTestFailsWithDeletedUser() throws Exception {
        saveMockUser();
        deleteMockUser();
        UserDto userDto = getMockUser();
        SignInRequest signinRequest = new ModelMapper().map(userDto, SignInRequest.class);
        mockMvc.perform(post("/api/v1/user/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signinRequest))
                ).andExpect(status().isNotFound());
    }

}
