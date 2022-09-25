package tech.portfolioshop.mailer.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

public abstract class SendEmailService {
    @Value("${spring.mail.username}")
    private String senderEmail;
    private String senderName = "Job Aggregator";
    private final JavaMailSender javaMailSender;
    protected SendEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendHTMLEmail(String toEmail, String subject, String body) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
        helper.setFrom(senderEmail, senderName);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body, true);
        javaMailSender.send(mail);
    }

    public void sendSimpleEmail(String toEmail, String subject, String body){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(senderEmail);
        mail.setTo(toEmail);
        mail.setSubject(subject);
        mail.setText(body);
        javaMailSender.send(mail);
    }
}