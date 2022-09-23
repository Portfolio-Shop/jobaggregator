package tech.portfolioshop.mailer.listeners;

import org.jobaggregator.kafka.payload.UserCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import tech.portfolioshop.mailer.services.SendEmailService;

import javax.mail.MessagingException;

@EnableKafka
@Component
public class WelcomeListener {

    private final SpringTemplateEngine templateEngine;
    private final SendEmailService sendEmailService;

    @Autowired
    public WelcomeListener(SpringTemplateEngine templateEngine, SendEmailService sendEmailService) {
        this.templateEngine = templateEngine;
        this.sendEmailService = sendEmailService;
    }

    @KafkaListener(topics = "USER_CREATED", groupId = "${spring.application.name}")
    public void userCreated(String message) throws MessagingException {
        UserCreated user = new UserCreated(null, null,null,null);
        user.deserialize(message);
        Context context = new Context();
        context.setVariable("username", user.getName());
        String body = templateEngine.process("WelcomeEmail", context);
        sendEmailService.sendEmail(user.getEmail(), "Welcome to the team", body);
    }
}
