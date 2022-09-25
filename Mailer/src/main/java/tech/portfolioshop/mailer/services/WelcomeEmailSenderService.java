package tech.portfolioshop.mailer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Service
public class WelcomeEmailSenderService extends SendEmailService{

    private final SpringTemplateEngine templateEngine;

    @Autowired
    public WelcomeEmailSenderService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        super(javaMailSender);
        this.templateEngine = templateEngine;
    }

    public void sendWelcomeEmail(String toEmail, String name) throws MessagingException, UnsupportedEncodingException {
        Context context = new Context();
        context.setVariable("username", name);
        String body = templateEngine.process("WelcomeEmail", context);
        String subject = "Welcome to the jobaggregator fam";
        sendHTMLEmail(toEmail, subject, body);
    }
}
