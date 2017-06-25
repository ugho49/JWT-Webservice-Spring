package fr.nantes.uste.demowebservice.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by ughostephan on 25/06/2017.
 */
@Service
public class MailService {

    @Resource
    private JavaMailSender mailSender;

    @Value("${mail.default.from}")
    private String defaultFrom;

    /**
     * Send mail with default "from" value.
     *
     * @param to      the to
     * @param subject the subject
     * @param message the message
     */
    public void sendMail(String to, String subject, String message) {
        send(defaultFrom, to, subject, message);
    }

    /**
     * Send mail with custom "from" value.
     *
     * @param from    the from
     * @param to      the to
     * @param subject the subject
     * @param message the message
     */
    public void sendMail(String from, String to, String subject, String message) {
        send(from, to, subject, message);
    }


    private void send(String from, String to, String subject, String message) {
        final SimpleMailMessage mail = new SimpleMailMessage();

        try {
            mail.setFrom(from);
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(message);
            mailSender.send(mail);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
