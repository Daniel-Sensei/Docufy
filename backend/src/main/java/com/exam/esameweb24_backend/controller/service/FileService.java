package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.persistence.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@CrossOrigin("http://localhost:4200/")
public class FileService {

    @GetMapping("/get-file")
    public ResponseEntity<Resource> getFile(HttpServletRequest req, @RequestParam("path") String path) {

        if (!checkAuthorization(req, path))
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

    @DeleteMapping("/delete-file")
    public ResponseEntity<String> deleteFile(HttpServletRequest req, @RequestParam("path") String path) {

        if (!checkAuthorization(req, path))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        File file = new File(path);
        if (file.exists()) {
            if (file.delete()) {
                return new ResponseEntity<>("File eliminato", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Errore durante l'eliminazione del file", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("File non trovato", HttpStatus.NOT_FOUND);
        }
    }

    private boolean checkAuthorization(HttpServletRequest req, String path) {
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);
        String pIvaFile = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("_"));

        // restituisce NON AUTORIZZATO se:
        // - l'utente non è loggato
        // - l'utente è un'azienda, ma non è associato al file
        // - l'utente è un consulente, ma non è associato all'azienda proprietaria del file o non è associato al file
        if (user == null)
            return false;
        if (!Utility.isConsultant(token) && !user.getPIva().equals(pIvaFile))
            return false;
        if (Utility.isConsultant(token) && !Utility.checkConsultantAgency(user.getPIva(), pIvaFile) && !user.getPIva().equals(pIvaFile))
            return false;
        return true;
    }
}
