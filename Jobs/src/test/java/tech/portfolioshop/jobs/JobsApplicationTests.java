package tech.portfolioshop.jobs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = JobsApplication.class)
@AutoConfigureMockMvc
class JobsApplicationTests {

    @Test
    void contextLoads() {
        assert true;
    }

}
