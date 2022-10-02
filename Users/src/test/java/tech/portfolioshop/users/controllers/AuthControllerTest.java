package tech.portfolioshop.users.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import tech.portfolioshop.users.models.request.SignInRequest;
import tech.portfolioshop.users.models.request.SignUpRequest;
import tech.portfolioshop.users.services.AuthService;
import tech.portfolioshop.users.shared.UserDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc
public class AuthControllerTest {

    @MockBean
    private AuthService authService;
    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    private final MockMvc mockMvc;

    @Autowired
    public AuthControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    private UserDto getMockUser(){
        UserDto userDto = new UserDto();
        userDto.setName("test");
        userDto.setEmail("test@test.test");
        userDto.setPhone("1234567890");
        userDto.setPassword("test1234567890");
        userDto.setUserId("test");
        return userDto;
    }

    @Test
    @DisplayName("Successful Sign Up on valid credentials")
    public void successfulSignup() throws Exception {
        UserDto userDto = getMockUser();
        SignUpRequest signUpRequest = new ModelMapper().map(userDto, SignUpRequest.class);
        Mockito.when(authService.signup(Mockito.any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(
                post("/api/v1/user/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequest))
        )
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.AUTHORIZATION));
        Mockito.verify(kafkaTemplate).send(Mockito.any(String.class), Mockito.any(String.class));
        Mockito.verify(authService).signup(Mockito.any(UserDto.class));
    }

    @Test
    @DisplayName("Failed Sign Up on incorrect email")
    public void invalidEmailSignup() throws Exception {
        UserDto userDto = getMockUser();
        SignUpRequest signUpRequest = new ModelMapper().map(userDto, SignUpRequest.class);
        signUpRequest.setEmail("test.test.test");
        Mockito.when(authService.signup(Mockito.any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(
                        post("/api/v1/user/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(signUpRequest))
                )
                .andExpect(status().isBadRequest());
        Mockito.verify(kafkaTemplate, Mockito.never()).send(Mockito.any(String.class), Mockito.any(String.class));
        Mockito.verify(authService, Mockito.never()).signup(Mockito.any(UserDto.class));
    }

    @Test
    @DisplayName("Failed Sign Up on no email")
    public void nullEmailSignup() throws Exception {
        UserDto userDto = getMockUser();
        SignUpRequest signUpRequest = new ModelMapper().map(userDto, SignUpRequest.class);
        signUpRequest.setEmail(null);
        Mockito.when(authService.signup(Mockito.any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(
                        post("/api/v1/user/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(signUpRequest))
                )
                .andExpect(
                        status().isBadRequest()
                );
        Mockito.verify(kafkaTemplate, Mockito.never()).send(Mockito.any(String.class), Mockito.any(String.class));
        Mockito.verify(authService, Mockito.never()).signup(Mockito.any(UserDto.class));
    }

    @Test
    @DisplayName("Failed Sign Up on invalid password")
    public void invalidPasswordSignup() throws Exception {
        UserDto userDto = getMockUser();
        SignUpRequest signUpRequest = new ModelMapper().map(userDto, SignUpRequest.class);
        signUpRequest.setPassword("test"); // password too short
        Mockito.when(authService.signup(Mockito.any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(
                        post("/api/v1/user/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(signUpRequest))
                )
                .andExpect(status().isBadRequest());
        signUpRequest.setPassword("test1234567890longPassword"); // password too long
        Mockito.when(authService.signup(Mockito.any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(
                        post("/api/v1/user/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(signUpRequest))
                )
                .andExpect(
                        status().isBadRequest()
                );
        Mockito.verify(kafkaTemplate, Mockito.never()).send(Mockito.any(String.class),Mockito.any(String.class));
        Mockito.verify(authService, Mockito.never()).signup(Mockito.any(UserDto.class));
    }

    @Test
    @DisplayName("Failed Sign Up on no password")
    public void nullPasswordSignup() throws Exception {
        UserDto userDto = getMockUser();
        SignUpRequest signUpRequest = new ModelMapper().map(userDto, SignUpRequest.class);
        signUpRequest.setPassword(null);
        Mockito.when(authService.signup(Mockito.any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(
                        post("/api/v1/user/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(signUpRequest))
                )
                .andExpect(
                        status().isBadRequest()
                );
        Mockito.verify(kafkaTemplate, Mockito.never()).send(Mockito.any(String.class), Mockito.any(String.class));
        Mockito.verify(authService, Mockito.never()).signup(Mockito.any(UserDto.class));
    }


    @Test
    @DisplayName("Successful Sign In on valid credentials")
    public void successfulSignin() throws Exception {
        UserDto userDto = getMockUser();
        SignInRequest signInRequest = new ModelMapper().map(userDto, SignInRequest.class);
        Mockito.when(authService.signin(Mockito.any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(
                post("/api/v1/user/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signInRequest))
        )
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.AUTHORIZATION));
        Mockito.verify(authService).signin(Mockito.any(UserDto.class));
    }

    @Test
    @DisplayName("Failed Sign In on invalid email")
    public void invalidEmailSignin() throws Exception {
        UserDto userDto = getMockUser();
        SignInRequest signInRequest = new ModelMapper().map(userDto, SignInRequest.class);
        signInRequest.setEmail("test.test.test");
        Mockito.when(authService.signin(Mockito.any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(
                post("/api/v1/user/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signInRequest))
        ).andExpect(status().isBadRequest());

        Mockito.verify(authService, Mockito.never()).signin(Mockito.any(UserDto.class));
    }

    @Test
    @DisplayName("Failed Sign In on missing email")
    public void nullEmailSignin() throws Exception {
        UserDto userDto = getMockUser();
        SignInRequest signInRequest = new ModelMapper().map(userDto, SignInRequest.class);
        signInRequest.setEmail(null);
        Mockito.when(authService.signin(Mockito.any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(
                post("/api/v1/user/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signInRequest))
        ).andExpect(status().isBadRequest());

        Mockito.verify(authService, Mockito.never()).signin(Mockito.any(UserDto.class));
    }

    @Test
    @DisplayName("Failed Sign In on missing password")
    public void nullPasswordSignin() throws Exception {
        UserDto userDto = getMockUser();
        SignInRequest signInRequest = new ModelMapper().map(userDto, SignInRequest.class);
        signInRequest.setPassword(null);
        Mockito.when(authService.signin(Mockito.any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(
                post("/api/v1/user/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signInRequest))
        ).andExpect(status().isBadRequest());

        Mockito.verify(authService, Mockito.never()).signin(Mockito.any(UserDto.class));
    }
}