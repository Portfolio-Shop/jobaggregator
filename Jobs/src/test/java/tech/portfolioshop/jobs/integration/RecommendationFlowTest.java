package tech.portfolioshop.jobs.integration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import tech.portfolioshop.jobs.JobsApplication;
import tech.portfolioshop.jobs.data.UserEntity;
import tech.portfolioshop.jobs.data.UserRepository;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = JobsApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.test.properties"
)
public class RecommendationFlowTest {
    private final MockMvc mockMvc;
    private final UserRepository userRepository;
    private final Environment environment;

    @Autowired
    public RecommendationFlowTest(
            MockMvc mockMvc,
            UserRepository userRepository,
            Environment environment) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.environment = environment;
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
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void testRecommendation() throws Exception {
        Auth auth = getMockAuth();
        mockMvc.perform(get("/api/v1/jobs")
                .header(HttpHeaders.AUTHORIZATION, auth.token()))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
//                   check content type
                    assert Objects.requireNonNull(result.getResponse().getContentType()).contains(MediaType.APPLICATION_JSON_VALUE);
                    // check if the response is an array
                    assert content.startsWith("[");
                    assert content.endsWith("]");
                });
    }
}
