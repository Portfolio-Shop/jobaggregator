package tech.portfolioshop.mailer.listeners;

import org.jobaggregator.kafka.config.KafkaTopics;
import org.jobaggregator.kafka.payload.UserCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import tech.portfolioshop.mailer.services.WelcomeEmailSenderService;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@EnableKafka
@Service
public class WelcomeListener {

    private final WelcomeEmailSenderService welcomeEmailSenderService;

    @Autowired
    public WelcomeListener(WelcomeEmailSenderService welcomeEmailSenderService) {
        this.welcomeEmailSenderService = welcomeEmailSenderService;
    }
    @KafkaListener(topics = KafkaTopics.USER_CREATED, groupId = "${spring.application.name}")
    public void userCreated(String message) throws MessagingException {
        UserCreated userCreated = new UserCreated().deserialize(message);
        welcomeEmailSenderService.sendWelcomeEmail(userCreated.getEmail(), userCreated.getName());
    }
}
