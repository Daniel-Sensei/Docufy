package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.persistence.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadFile(HttpServletRequest req, @RequestParam("file") MultipartFile file) {

        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);

        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if (file.isEmpty()) {
            return new ResponseEntity<>("Nessun file selezionato", HttpStatus.BAD_REQUEST);
        }

        try {
            String fileName = generateFileName(user.getPIva(), file);
            String filePath = uploadDir + "/" + fileName;
            Path destination = Paths.get(filePath);

            Files.write(destination, file.getBytes());

            return new ResponseEntity<>(filePath, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>("Errore durante il caricamento del file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-file")
    public ResponseEntity<Resource> getFile(HttpServletRequest req, @RequestParam("path") String path) {

        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);
        String pIvaFile = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("_"));

        // restituisce NON AUTORIZZATO se:
        // - l'utente non è loggato
        // - l'utente è un'azienda, ma non è associato al file
        // - l'utente è un consulente, ma non è associato all'azienda proprietaria del file o non è associato al file
        if (user == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (!Utility.isConsultant(token) && !user.getPIva().equals(pIvaFile))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (Utility.isConsultant(token) && !Utility.checkConsultantAgency(user.getPIva(), pIvaFile) && !user.getPIva().equals(pIvaFile))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);


        File file = new File(path);
        if (file.exists()) {
            try {
                byte[] fileContent = Files.readAllBytes(file.toPath());
                ByteArrayResource resource = new ByteArrayResource(fileContent);

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(file.length())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private String generateFileName(String prefix, MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        assert originalFileName != null;
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        return prefix + "_" + System.currentTimeMillis() + extension;
    }

}
