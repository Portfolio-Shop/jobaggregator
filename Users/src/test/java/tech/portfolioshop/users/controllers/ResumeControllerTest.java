package tech.portfolioshop.users.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import tech.portfolioshop.users.services.ResumeService;
import tech.portfolioshop.users.shared.ResumeDto;
import tech.portfolioshop.users.shared.UserDto;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ResumeController.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.test.properties")
class ResumeControllerTest {

    @MockBean
    private ResumeService resumeService;

    private final MockMvc mvc;
    private final Environment environment;

    @Autowired
    ResumeControllerTest(MockMvc mvc, Environment environment) {
        this.mvc = mvc;
        this.environment = environment;
    }


    private ResumeDto getMockResume(){
        ResumeDto resumeDto = new ResumeDto();
        byte[] pdfBody = new byte[200];
        new Random().nextBytes(pdfBody);
        resumeDto.setResume(pdfBody);
        resumeDto.setUserId(getMockUser().getUserId());
        return resumeDto;
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
    @DisplayName("User can upload resume")
    public void uploadResume() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("resume", "test.pdf",
                MediaType.APPLICATION_PDF_VALUE, getMockResume().getResume());
        String token = "Bearer " + Jwts.builder()
                .setSubject(getMockUser().getUserId())
                .signWith(SignatureAlgorithm.HS256, environment.getProperty("jwt.secret"))
                .compact();
        mvc.perform( multipart("/api/v1/user/resume").file(multipartFile)
                        .header(HttpHeaders.AUTHORIZATION, token)
                )
                .andExpect(status().isCreated());
        Mockito.verify(resumeService).uploadResume(Mockito.any(ResumeDto.class));
    }

    @Test
    @DisplayName("User can't upload resume with invalid token")
    public void uploadResumeWithInvalidToken() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("resume", "test.pdf",
                MediaType.APPLICATION_PDF_VALUE, getMockResume().getResume());
        String token = "Bearer " + Jwts.builder()
                .setSubject(getMockUser().getUserId())
                .signWith(SignatureAlgorithm.HS256, "fakeSecret")
                .compact();
        mvc.perform( multipart("/api/v1/user/resume").file(multipartFile)
                        .header(HttpHeaders.AUTHORIZATION, token)
                )
                .andExpect(status().isUnauthorized());
        Mockito.verify(resumeService, Mockito.never()).uploadResume(Mockito.any(ResumeDto.class));
    }

    @Test
    @DisplayName("User can't upload resume without token")
    public void uploadResumeWithoutToken() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("resume", "test.pdf",
                MediaType.APPLICATION_PDF_VALUE, getMockResume().getResume());
        mvc.perform( multipart("/api/v1/user/resume").file(multipartFile))
                .andExpect(status().isBadRequest());
        Mockito.verify(resumeService, Mockito.never()).uploadResume(Mockito.any(ResumeDto.class));
    }

    @Test
    @DisplayName("User can get resume")
    public void getResume() throws Exception {
        String token = "Bearer " + Jwts.builder()
                .setSubject(getMockUser().getUserId())
                .signWith(SignatureAlgorithm.HS256, environment.getProperty("jwt.secret"))
                .compact();
        Mockito.when(resumeService.getResume(Mockito.any(String.class))).thenReturn(getMockResume());
        mvc.perform( get("/api/v1/user/resume")
                        .header(HttpHeaders.AUTHORIZATION, token)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF_VALUE));
        Mockito.verify(resumeService).getResume(Mockito.any(String.class));
    }
    @Test
    @DisplayName("User can't get resume with invalid token")
    public void getResumeInvalidToken() throws Exception {
        String token = "Bearer " + Jwts.builder()
                .setSubject(getMockUser().getUserId())
                .signWith(SignatureAlgorithm.HS256, "fakeToken")
                .compact();
        Mockito.when(resumeService.getResume(Mockito.any(String.class))).thenReturn(getMockResume());
        mvc.perform( get("/api/v1/user/resume")
                        .header(HttpHeaders.AUTHORIZATION, token)
                )
                .andExpect(status().isUnauthorized());
        Mockito.verify(resumeService, Mockito.never()).getResume(Mockito.any(String.class));
    }
    @Test
    @DisplayName("User can't get resume without token")
    public void getResumeWithoutToken() throws Exception {
        Mockito.when(resumeService.getResume(Mockito.any(String.class))).thenReturn(getMockResume());
        mvc.perform( get("/api/v1/user/resume"))
                .andExpect(status().isBadRequest());
        Mockito.verify(resumeService, Mockito.never()).getResume(Mockito.any(String.class));
    }
}