package com.exam.esameweb24_backend.controller.service.impl;

import com.exam.esameweb24_backend.controller.service.EmailService;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public String sendMail(String to, String[] cc, String subject, String body) {
        Properties props = new Properties();

        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.gmail.com");

        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.port", "587");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", false);


        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication  getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                "testforwebapplication@gmail.com", "gfqopfdisafxscpz");
                    }
                });

        try{
            Transport transport = session.getTransport();
            MimeMessage msg = new MimeMessage(session);
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setFrom(fromEmail);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(String.join(",", cc)));
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(body);
            transport.connect();
            Transport.send(msg);
            transport.close();

            return "mail sent";
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
