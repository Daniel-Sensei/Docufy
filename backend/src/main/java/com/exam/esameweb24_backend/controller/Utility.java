package com.exam.esameweb24_backend.controller;

import com.exam.esameweb24_backend.persistence.model.Documento;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

public class Utility {

    private static final String uploadDir = "./backend/src/main/resources/files";

    public static String encodeBase64(String value){
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

    public static String decodeBase64(String value){
        return new String(Base64.getDecoder().decode(value.getBytes()));
    }

    public static String getToken(HttpServletRequest req){
        String auth = req.getHeader("Authorization");
        if (auth == null) return "";
        return auth.substring("Basic ".length());
    }

    public static String getTokenEmail(String token){
        return decodeBase64(token).split(":tkn:")[0];
    }

    public static String getTokenPassword(String token){
        return decodeBase64(token).split(":tkn:")[1];
    }

    public static String getTokenRole(String token){
        return decodeBase64(token).split(":tkn:")[2];
    }

    public static Boolean isConsultant(String token) {
        return getTokenRole(token).equals("C");
    }


    public static Boolean checkConsultantAgency(String consultant, String agency) {
        return (DBManager.getInstance().getAziendaDao().findByConsultant(consultant).stream().anyMatch(azienda -> azienda.getPIva().equals(agency)));
    }

    public static Boolean checkAgencyEmployeeID(String agency, Long employee) {
        return (DBManager.getInstance().getDipendenteDao().findById(employee).getAzienda().getPIva().equals(agency));
    }
    public static Boolean checkAgencyEmployeeCF(String agency, String cf) {
        return (DBManager.getInstance().getDipendenteDao().findByCF(cf).getAzienda().getPIva().equals(agency));
    }

    public static boolean checkPassword(String plainPW, User storedUser){
        return BCrypt.checkpw(plainPW, storedUser.getPassword());
    }
    public static User getRequestUser(HttpServletRequest req){
        return DBManager.getInstance().getUserDao().findByToken(getToken(req));
    }

    public static String generateFileName(String prefix, MultipartFile file) {
        // prende il nome del file originale
        String originalFileName = file.getOriginalFilename();
        // si assicura che il nome non sia nullo
        assert originalFileName != null;
        // si assicura che il file abbia un'estensione
        assert originalFileName.contains(".");
        // prende l'estensione del file
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        // restituisce il nuovo nome del file
        return prefix + "_" + System.currentTimeMillis() + extension;
    }

    public static <T> T jsonToObject(MultipartFile file, Class<T> valueType) {
        try {
            byte[] fileBytes = file.getBytes();
            String jsonString = new String(fileBytes);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String uploadFile(String pIva, MultipartFile file) throws IOException {
        String fileName = Utility.generateFileName(pIva, file);
        String filePath = uploadDir + "/" + fileName;

        Path destination = Paths.get(filePath);
        Files.write(destination, file.getBytes());

        return filePath;
    }

    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (file.exists())
            return file.delete();
        return false;
    }

    //funzione che aggiorna lo stato di tutti i documenti in base al tempo restante fino alla data di scadenza
    // oltre 1 mese -> "Valido"
    // 1 mese o meno -> "In Scadenza"
    // troppo tardi -> "Scaduto"
    public static void updateAllDocumentsState(){

        List<Documento> inScadenza = new ArrayList<>();
        List<Documento> scaduto = new ArrayList<>();

        DBManager.getInstance().getDocumentoDao().getAll().forEach(documento -> {

            if(documento.getDataScadenza().getTime() - System.currentTimeMillis() > 2592000000L)
                documento.setStato("Valido");
            else if(documento.getDataScadenza().getTime() - System.currentTimeMillis() > 0) {
                documento.setStato("In Scadenza");
                inScadenza.add(documento);
            }
            else {
                documento.setStato("Scaduto");
                scaduto.add(documento);
            }
            DBManager.getInstance().getDocumentoDao().update(documento);
        });

        while (!inScadenza.isEmpty() || !scaduto.isEmpty()) {

            List<Documento> inScadenzaSend = new ArrayList<>();
            List<Documento> scadutoSend = new ArrayList<>();

            String emailTo = "";
            String[] cc = new String[2];
            String subject = "Notifica Scadenza Documenti";

            if (!inScadenza.isEmpty()) {
                inScadenzaSend.add(inScadenza.get(0));
                inScadenza.remove(0);
                emailTo = inScadenzaSend.get(0).getAzienda().getEmail();
                cc[0] = inScadenzaSend.get(0).getAzienda().getEmail();
                cc[1] = inScadenzaSend.get(0).getAzienda().getConsulente().getEmail();

                // Creazione di un iteratore per evitare IndexOutOfBoundsException
                Iterator<Documento> iterator = inScadenza.iterator();
                if (iterator.hasNext()) {
                    do {
                        Documento documento = iterator.next();
                        if (inScadenzaSend.get(0).getAzienda().equals(documento.getAzienda())) {
                            inScadenzaSend.add(documento);
                            iterator.remove(); // Rimuove l'elemento corrente in modo sicuro
                        }
                    } while (iterator.hasNext());
                }
                Iterator<Documento> iterator2 = scaduto.iterator();
                while (iterator2.hasNext()) {
                    Documento documento = iterator2.next();
                    if (inScadenza.get(0).getAzienda().equals(documento.getAzienda())) {
                        scadutoSend.add(documento);
                        iterator2.remove(); // Rimuove l'elemento corrente in modo sicuro
                    }
                }
            } else {
                scadutoSend.add(scaduto.get(0));
                scaduto.remove(0);
                emailTo = scadutoSend.get(0).getAzienda().getEmail();
                cc[0] = scadutoSend.get(0).getAzienda().getEmail();
                cc[1] = scadutoSend.get(0).getAzienda().getConsulente().getEmail();

                // Creazione di un iteratore per evitare IndexOutOfBoundsException
                Iterator<Documento> iterator = scaduto.iterator();
                if (iterator.hasNext()) {
                    do {
                        Documento documento = iterator.next();
                        if (scadutoSend.get(0).getAzienda().equals(documento.getAzienda())) {
                            scadutoSend.add(documento);
                            iterator.remove(); // Rimuove l'elemento corrente in modo sicuro
                        }
                    } while (iterator.hasNext());
                }
            }
            EmailSender.sendDocumentExpirationMail(emailTo, cc, subject, inScadenzaSend, scadutoSend);
        }
    }
}
