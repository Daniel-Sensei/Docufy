package com.exam.esameweb24_backend.controller.service.impl;

import com.exam.esameweb24_backend.controller.service.EmailService;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;

import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            if(cc != null)
                msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(String.join(",", cc)));
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            if(body.split(":")[0].equals("registration"))
                msg.setContent(sendRegistrationMail(body));
            else if(body.split(":")[0].equals("documentExpiration"))
                msg.setContent(sendDocumentExpirationMail(body));
            //other else if for other types of mail

            transport.connect();
            Transport.send(msg);
            transport.close();

            return "mail sent";
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private Multipart sendRegistrationMail(String body) throws MessagingException, IOException {
        String mailTo = body.split(":")[1];
        String passwordTo = body.split(":")[2];

        // Read HTML content from file
        String htmlContent = readFile("backend/src/main/resources/mailStuff/emailCredentials.html");

        // Replace placeholder with the actual value
        htmlContent = htmlContent.replace("email@esempio.com", mailTo);
        htmlContent = htmlContent.replace("HJsnks8!", passwordTo);

        //TODO: cambiare anche i link per accettare ecc..

        // Read CSS content from file (if needed)
        // String cssContent = readFile("backend/src/main/resources/mailStuff/styleEmail.css");

        // Create the HTML part
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(htmlContent, "text/html; charset=utf-8");

        // Create the multipart message
        Multipart multipart = new MimeMultipart();
        // Aggiungi il contenuto HTML al corpo del messaggio
        multipart.addBodyPart(htmlPart);

        // Aggiungi le immagini come parti del corpo, utilizzando Content-ID per riferirsi ad esse nell'HTML
        MimeBodyPart imagePartLogo = new MimeBodyPart();
        File logo = new File("backend/src/main/resources/mailStuff/images/logo.png");
        imagePartLogo.attachFile(logo);
        imagePartLogo.setContentID("<logo>");
        imagePartLogo.setDisposition(MimeBodyPart.INLINE);
        multipart.addBodyPart(imagePartLogo);

        MimeBodyPart imagePartHost = new MimeBodyPart();
        File host = new File("backend/src/main/resources/mailStuff/images/image-host.png");
        imagePartHost.attachFile(host);
        imagePartHost.setContentID("<host>");
        imagePartHost.setDisposition(MimeBodyPart.INLINE);
        multipart.addBodyPart(imagePartHost);




        // Incorporate CSS into HTML
        // htmlContent = htmlContent.replace("</head>", "<style>" + cssContent + "</style></head>");

        return multipart;
    }

    private Multipart sendDocumentExpirationMail(String body){
        //TODO: implement this method
        return null;
    }


    // Utility method to read file content
    private String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] fileBytes = Files.readAllBytes(path);
        return new String(fileBytes, StandardCharsets.UTF_8);
    }
}
