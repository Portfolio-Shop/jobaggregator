package utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class SendEmail {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendEmail(String toEmail, String subject) throws MessagingException {
        MimeMessage mail = javaMailSender.createMimeMessage();
        String body = "<h1>Hello</h1>";
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setFrom("jobsearchaggregator@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body, true);
        javaMailSender.send(mail);
    }
}