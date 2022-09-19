package tech.portfolioshop.users.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import tech.portfolioshop.users.models.http.request.UserUpdateRequest;
import tech.portfolioshop.users.services.ProfileService;
import tech.portfolioshop.users.shared.UserDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProfileController.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.test.properties")
public class ProfileControllerTest {
    @MockBean
    private ProfileService profileService;
    @MockBean
    private KafkaTemplate<String,String> kafkaTemplate;

    private final MockMvc mockMvc;
    private final Environment environment;

    @Autowired
    public ProfileControllerTest(MockMvc mockMvc, Environment environment) {
        this.mockMvc = mockMvc;
        this.environment = environment;
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
    @DisplayName("User can get profile if authorized")
    public void GetProfileWithToken() throws Exception {
        String token = "Bearer " + Jwts.builder()
                .setSubject(getMockUser().getUserId())
                .signWith(SignatureAlgorithm.HS256, environment.getProperty("jwt.secret"))
                .compact();
        Mockito.when(profileService.getUserByUserId(Mockito.any(String.class))).thenReturn(getMockUser());
        mockMvc.perform(
                get("/api/v1/user/profile")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        Mockito.verify(profileService).getUserByUserId(Mockito.any(String.class));
    }
    @Test
    @DisplayName("User can't get profile without token")
    public void GetProfileWithoutToken() throws Exception {
        Mockito.when(profileService.getUserByUserId(Mockito.any(String.class))).thenReturn(getMockUser());
        mockMvc.perform(
                get("/api/v1/user/profile")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
        Mockito.verify(profileService, Mockito.never()).getUserByUserId(Mockito.any(String.class));
    }

    @Test
    @DisplayName("User can't get profile without valid token")
    public void GetProfileWithoutValidToken() throws Exception {
        String token = "Bearer " + Jwts.builder()
                .setSubject(getMockUser().getUserId())
                .signWith(SignatureAlgorithm.HS256, "fakeSecret")
                .compact();
        Mockito.when(profileService.getUserByUserId(Mockito.any(String.class))).thenReturn(getMockUser());
        mockMvc.perform(
                get("/api/v1/user/profile")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized());
        Mockito.verify(profileService, Mockito.never()).getUserByUserId(Mockito.any(String.class));
    }

    @Test
    @DisplayName("Profile can be updated with correct body and correct authorization token")
    public void updateUserWithValidTokenAndBody() throws Exception {
        String token = "Bearer " + Jwts.builder()
                .setSubject(getMockUser().getUserId())
                .signWith(SignatureAlgorithm.HS256, environment.getProperty("jwt.secret"))
                .compact();
        UserUpdateRequest userUpdateRequest = new ModelMapper().map(getMockUser(), UserUpdateRequest.class);
        mockMvc.perform(
                put("/api/v1/user/profile")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userUpdateRequest))
        ).andExpect(status().isNoContent());

        Mockito.verify(kafkaTemplate).send(Mockito.any(String.class), Mockito.any(String.class));
        Mockito.verify(profileService).updateUser(Mockito.any(UserDto.class));
    }

    @Test
    @DisplayName("Profile can't be updated with incorrect authorization token")
    public void updateUserWithInvalidToken() throws Exception {
        String token = "Bearer " + Jwts.builder()
                .setSubject(getMockUser().getUserId())
                .signWith(SignatureAlgorithm.HS256, "fakeSecret")
                .compact();
        UserUpdateRequest userUpdateRequest = new ModelMapper().map(getMockUser(), UserUpdateRequest.class);
        mockMvc.perform(
                put("/api/v1/user/profile")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userUpdateRequest))
        ).andExpect(status().isUnauthorized());
        Mockito.verify(kafkaTemplate, Mockito.never()).send(Mockito.any(String.class), Mockito.any(String.class));
        Mockito.verify(profileService, Mockito.never()).updateUser(Mockito.any(UserDto.class));
    }

    @Test
    @DisplayName("Profile can't be updated without token")
    public void updateUserWithoutToken() throws Exception {
        UserUpdateRequest userUpdateRequest = new ModelMapper().map(getMockUser(), UserUpdateRequest.class);
        mockMvc.perform(
                put("/api/v1/user/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userUpdateRequest))
        ).andExpect(status().isBadRequest());
        Mockito.verify(kafkaTemplate, Mockito.never()).send(Mockito.any(String.class), Mockito.any(String.class));
        Mockito.verify(profileService, Mockito.never()).updateUser(Mockito.any(UserDto.class));
    }

    @Test
    @DisplayName("Profile can't be updated without body")
    public void updateUserWithoutBody() throws Exception {
        String token = "Bearer " + Jwts.builder()
                .setSubject(getMockUser().getUserId())
                .signWith(SignatureAlgorithm.HS256, environment.getProperty("jwt.secret"))
                .compact();
        mockMvc.perform(
                put("/api/v1/user/profile")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
        Mockito.verify(kafkaTemplate, Mockito.never()).send(Mockito.any(String.class), Mockito.any(String.class));
        Mockito.verify(profileService, Mockito.never()).updateUser(Mockito.any(UserDto.class));
    }

    @Test
    @DisplayName("Profile can be deleted with valid token")
    public void deleteWithToken() throws Exception {
        String token = "Bearer " + Jwts.builder()
                .setSubject(getMockUser().getUserId())
                .signWith(SignatureAlgorithm.HS256, environment.getProperty("jwt.secret"))
                .compact();
        mockMvc.perform(
                delete("/api/v1/user/profile")
                        .header(HttpHeaders.AUTHORIZATION, token)
        ).andExpect(status().isNoContent());
        Mockito.verify(kafkaTemplate).send(Mockito.any(String.class), Mockito.any(String.class));
        Mockito.verify(profileService).deleteUser(Mockito.any(String.class));
    }

    @Test
    @DisplayName("Profile can't be deleted without token")
    public void deleteWithoutToken() throws Exception {
        mockMvc.perform(
                delete("/api/v1/user/profile")
        ).andExpect(status().isBadRequest());
        Mockito.verify(kafkaTemplate, Mockito.never()).send(Mockito.any(String.class), Mockito.any(String.class));
        Mockito.verify(profileService, Mockito.never()).deleteUser(Mockito.any(String.class));
    }

    @Test
    @DisplayName("Profile can't be deleted with invalid token")
    public void deleteWithInvalidToken() throws Exception {
        String token = "Bearer " + Jwts.builder()
                .setSubject(getMockUser().getUserId())
                .signWith(SignatureAlgorithm.HS256, "fakeSecret")
                .compact();
        mockMvc.perform(
                delete("/api/v1/user/profile")
                        .header(HttpHeaders.AUTHORIZATION, token)
        ).andExpect(status().isUnauthorized());
        Mockito.verify(kafkaTemplate, Mockito.never()).send(Mockito.any(String.class), Mockito.any(String.class));
        Mockito.verify(profileService, Mockito.never()).deleteUser(Mockito.any(String.class));
    }
}