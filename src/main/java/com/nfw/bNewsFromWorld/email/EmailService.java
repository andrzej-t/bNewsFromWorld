package com.nfw.bNewsFromWorld.email;

import com.nfw.bNewsFromWorld.exception.WrongEmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService implements EmailSender {

    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void send(String to, String email) {
        try {
            to = new String(to.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("newsfromworld@interia.pl");
            try {
                mailSender.send(mimeMessage);
            } catch (MailSendException e) {
                throw new WrongEmailException("");
            }
        } catch (MessagingException | MailSendException e) {
            LOGGER.error("Failed to send email  ", e);
            throw new WrongEmailException("");
        }
    }
}
