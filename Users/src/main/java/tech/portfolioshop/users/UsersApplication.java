package tech.portfolioshop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class UsersApplication {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public UsersApplication(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @EventListener(ApplicationReadyEvent.class)
    public void connectToKafka() {
        kafkaTemplate.send("connect", "connect");
    }
}
