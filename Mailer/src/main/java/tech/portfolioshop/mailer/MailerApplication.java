package tech.portfolioshop.mailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import tech.portfolioshop.mailer.services.SendEmailService;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SpringBootApplication
public class MailerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MailerApplication.class, args);
    }
}
