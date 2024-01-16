package com.exam.esameweb24_backend.controller;

import com.exam.esameweb24_backend.persistence.model.Documento;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class EmailSender {

    private static final String fromEmail = "testforwebapplication@gmail.com";


    private static Boolean sendMail(String to, String[] cc, String subject, Multipart content) {
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
                        return new PasswordAuthentication("testforwebapplication@gmail.com", "gfqopfdisafxscpz");
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
            msg.setContent(content);

            //other else if for other types of mail

            transport.connect();
            Transport.send(msg);
            transport.close();

            return true;

        } catch (Exception e){
            return false;
        }
    }

    // funzione per inviare una mail di conferma ricezione
    public static Boolean sendConfirmationMail(String to, String[] cc, String subject, String body){
        try {
            Multipart multipart = new MimeMultipart();
            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(body);
            multipart.addBodyPart(messageBodyPart);

            return sendMail(to, cc, subject, multipart);

        } catch (MessagingException e) {
            return false;
        }
    }

    // funzione per inviare una mail di registrazione
    public static Boolean sendRegistrationMail(String to, String[] cc, String subject, String body){
        try {
            String mailTo = body.split(":")[0];
            String passwordTo = body.split(":")[1];

            // Read HTML content from file
            String htmlContent = readFile("backend/src/main/resources/mailStuff/emailCredentials.html");

            // Replace placeholder with the actual value
            htmlContent = htmlContent.replace("email@esempio.com", mailTo);
            htmlContent = htmlContent.replace("HJsnks8!", passwordTo);

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

            return sendMail(to, cc, subject, multipart);
        } catch (MessagingException | IOException e) {
            return false;
        }
    }

    // funzione per inviare una mail di scadenza documento
    public static Boolean sendDocumentExpirationMail(String to, String[] cc, String subject, List<Documento> inScadenza, List<Documento> scaduto){

        // TODO: implementare funzione

        if(inScadenza.isEmpty() && scaduto.isEmpty()) return false;

        try{
            // Read HTML content from file
            String htmlContent = readFile("backend/src/main/resources/mailStuff/avviso-scadenza.html");

            if(!inScadenza.isEmpty()) {
                List<String> id = new ArrayList<>();
                List<String> nomi = new ArrayList<>();
                List<String> scadenze = new ArrayList<>();
                inScadenza.forEach(documento -> {
                    if(documento.getAzienda()==null)
                        id.add(documento.getDipendente().getCF());
                    else
                        id.add(documento.getAzienda().getPIva());
                    nomi.add(documento.getNome());
                    scadenze.add(new SimpleDateFormat("dd/MM/yyyy").format(documento.getDataScadenza()));
                });

                String inScadHTML = readFile("backend/src/main/resources/mailStuff/in-scadenza.html");
                StringBuilder inScadTable = new StringBuilder();
                for(int i = 0; i < nomi.size(); i++){
                    inScadTable.append("<tr>\n"+
                            "<td class=\"pad\"\n"+
                            "style=\"padding-bottom:10px;padding-left:50px;padding-right:50px;padding-top:10px;\">\n"+
                            "<div\n"+
                            "style=\"color:#393d47;font-family:'Helvetica Neue', Helvetica, Arial, sans-serif;font-size:13px;font-weight:400;line-height:150%;text-align:left;mso-line-height-alt:19.5px;\">\n"+
                            "<p style=\"margin: 0;\"><span\n"+
                            "style=\"color: #000000;\">"
                    ).append(id.get(i)).append(" - ").append(nomi.get(i)).append(" - ").append(scadenze.get(i))
                            .append("</span></p>\n").append("</div>\n").append("</td>\n");
                }
                inScadHTML = inScadHTML.replace("<!-- DOCUMENTI_IN_SCADENZA -->", inScadTable.toString());
                htmlContent = htmlContent.replace("<!-- BLOCCO_IN_SCADENZA -->", inScadHTML);
            }

            if(!scaduto.isEmpty()) {
                List<String> id = new ArrayList<>();
                List<String> nomi = new ArrayList<>();
                List<String> scadenze = new ArrayList<>();
                scaduto.forEach(documento -> {
                    if(documento.getAzienda()==null)
                        id.add(documento.getDipendente().getCF());
                    else
                        id.add(documento.getAzienda().getPIva());
                    nomi.add(documento.getNome());
                    scadenze.add(new SimpleDateFormat("dd/MM/yyyy").format(documento.getDataScadenza()));
                });

                String scadHTML = readFile("backend/src/main/resources/mailStuff/scaduti.html");
                StringBuilder scadTable = new StringBuilder();
                for(int i = 0; i < nomi.size(); i++){
                    scadTable.append("<tr>\n"+
                                    "<td class=\"pad\"\n"+
                                    "style=\"padding-bottom:10px;padding-left:50px;padding-right:50px;padding-top:10px;\">\n"+
                                    "<div\n"+
                                    "style=\"color:#393d47;font-family:'Helvetica Neue', Helvetica, Arial, sans-serif;font-size:13px;font-weight:400;line-height:150%;text-align:left;mso-line-height-alt:19.5px;\">\n"+
                                    "<p style=\"margin: 0;\"><span\n"+
                                    "style=\"color: #000000;\">"
                            ).append(id.get(i)).append(" - ").append(nomi.get(i)).append(" - ").append(scadenze.get(i))
                            .append("</span></p>\n").append("</div>\n").append("</td>\n");
                }
                scadHTML = scadHTML.replace("<!-- DOCUMENTI_SCADUTI -->", scadTable.toString());
                htmlContent = htmlContent.replace("<!-- BLOCCO_SCADUTI -->", scadHTML);
            }

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

            return sendMail(to, cc, subject, multipart);
        } catch (Exception e){
            return false;
        }
    }


    // Utility method to read file content
    private static String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] fileBytes = Files.readAllBytes(path);
        return new String(fileBytes, StandardCharsets.UTF_8);
    }
}
