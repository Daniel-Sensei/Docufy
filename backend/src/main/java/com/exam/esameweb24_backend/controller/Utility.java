package com.exam.esameweb24_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.Dipendente;
import com.exam.esameweb24_backend.persistence.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

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
        return decodeBase64(token).split(":")[0];
    }

    public static String getTokenPassword(String token){
        return decodeBase64(token).split(":")[1];
    }

    public static String getTokenRole(String token){
        return decodeBase64(token).split(":")[2];
    }

    public static Boolean isConsultant(String token) {
        return getTokenRole(token).equals("C");
    }

    public static Boolean checkConsultantAgency(String consultant, String agency) {
        return (DBManager.getInstance().getAziendaDao().findByConsultant(consultant).stream().anyMatch(azienda -> azienda.getPIva().equals(agency)));
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

    public static Dipendente jsonToDipendente(MultipartFile file){
        try {
            // Converti il MultipartFile in un array di byte
            byte[] fileBytes = file.getBytes();

            // Converti l'array di byte in una stringa JSON
            String jsonString = new String(fileBytes);

            // Deserializza il JSON in un oggetto Dipendente
            ObjectMapper oM = new ObjectMapper();
            return oM.readValue(jsonString, Dipendente.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String uploadFile(MultipartFile file, User user) throws IOException {
        String fileName = Utility.generateFileName(user.getPIva(), file);
        String filePath = uploadDir + "/" + fileName;

        Path destination = Paths.get(filePath);
        Files.write(destination, file.getBytes());

        return filePath;
    }
}
