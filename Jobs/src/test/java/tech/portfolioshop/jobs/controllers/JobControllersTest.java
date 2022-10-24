package tech.portfolioshop.jobs.controllers;

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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import tech.portfolioshop.jobs.services.JobsService;
import org.springframework.http.HttpHeaders;
import tech.portfolioshop.jobs.shared.JobsDto;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobControllers.class)
@AutoConfigureMockMvc
class JobControllersTest {
    @MockBean
    private JobsService jobsService;

    @MockBean
    KafkaTemplate<String, String> kafkaTemplate;

    private final MockMvc mockMvc;
    private final Environment environment;

    @Autowired
    JobControllersTest(MockMvc mockMvc, Environment environment) {
        this.mockMvc = mockMvc;
        this.environment = environment;
    }

    private List<String> getMockAuth() {
        String userId = "test-userid";
        return List.of(userId, "Bearer " + Jwts.builder()
                .setSubject(userId)
                .signWith(SignatureAlgorithm.HS256, environment.getProperty("jwt.secret"))
                .compact());
    }

    private List<JobsDto> getMockJobs() {
        List<JobsDto> ret = new ArrayList<>();
        JobsDto job1 = new JobsDto(
                "test-id-1", "test-query-1",
                "test-location-1", "test-title-1",
                "test-company-1", "test-salary-1",
                "<p>test-description-1</p>",
                List.of("test-tag-1", "test-tag-2")
        );
        JobsDto job2 = new JobsDto(
                "test-id-2", "test-query-2",
                "test-location-2", "test-title-2",
                "test-company-2", "test-salary-2",
                "<p>test-description-2</p>",
                List.of("test-tag-3", "test-tag-4")
        );
        ret.add(job1);
        ret.add(job2);
        return ret;
    }


    @Test
    @DisplayName("GET jobs successfully")
    public void getJobRecommendationSuccessfully() throws Exception {
        String token = getMockAuth().get(1);
        String userId = getMockAuth().get(0);
        Mockito.when(jobsService.findJobByRecommendation(userId)).thenReturn(getMockJobs());

        mockMvc.perform(get("/api/v1/jobs")
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    assert content.contains("test-id-1");
                    assert content.contains("test-id-2");
//                    check response is a list
                    assert content.startsWith("[");
                    assert content.endsWith("]");
                });
    }

    @Test
    @DisplayName("GET jobs failed with invalid token")
    public void getJobRecommendationFailedWithInvalidToken() throws Exception {
        String token = "invalid-token";
        mockMvc.perform(get("/api/v1/jobs")
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET jobs failed with no token")
    public void getJobRecommendationFailedWithNoToken() throws Exception {
        mockMvc.perform(get("/api/v1/jobs"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Read jobs with query successful with no token")
    public void getJobWithQueryFailedWithNoToken() throws Exception {
        Mockito.when(jobsService.findJobByQuery("test-query", "test-location")).thenReturn(getMockJobs());
        mockMvc.perform(get("/api/v1/jobs?query=test-query&location=test-location"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    assert content.contains("test-id-1");
                    assert content.contains("test-id-2");
//                    check response is a list
                    assert content.startsWith("[");
                    assert content.endsWith("]");
                });
    }
    @Test
    @DisplayName("Read jobs with query successful withtoken")
    public void getJobWithQueryFailedWithToken() throws Exception {
        String token = getMockAuth().get(1);
        String userId = getMockAuth().get(0);
        Mockito.when(jobsService.findJobByQuery("test-query", "test-location", userId)).thenReturn(getMockJobs());
        mockMvc.perform(get("/api/v1/jobs?query=test-query&location=test-location")
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    assert content.contains("test-id-1");
                    assert content.contains("test-id-2");
//                    check response is a list
                    assert content.startsWith("[");
                    assert content.endsWith("]");
                });
    }
}