package tech.portfolioshop.users;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import tech.portfolioshop.users.models.kafka.Payload;
import tech.portfolioshop.users.models.kafka.UserCreated;
import tech.portfolioshop.users.models.kafka.UserDeleted;
import tech.portfolioshop.users.models.kafka.UserUpdated;
import tech.portfolioshop.users.services.KafkaProducerService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = UsersApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.test.properties")
public class UsersApplicationTests {

    private final MockMvc mvc;
    @Autowired
    public UsersApplicationTests(MockMvc mvc) {
        this.mvc = mvc;
    }
    @Test
    public void unauthorizedGetProfile() throws Exception {
        mvc.perform(get("/api/v1/user/profile")
                        .contentType(MediaType.TEXT_PLAIN))
                        .andExpect(status().isBadRequest());
    }

}
