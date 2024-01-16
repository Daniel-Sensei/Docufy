package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.persistence.DBManager;
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

        if (!checkAuthorization(req, path)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

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
    public static ResponseEntity<String> deleteFile(HttpServletRequest req, @RequestParam("path") String path) {

        if (!checkAuthorization(req, path))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return Utility.deleteFile(path) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static boolean checkAuthorization(HttpServletRequest req, String path) {

        User user = Utility.getRequestUser(req);
        String pIvaFile = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("_"));

        // restituisce NON AUTORIZZATO se:
        // - l'utente non è loggato
        // - l'utente è un'azienda, ma non è associato al file
        // - l'utente è un consulente, ma non è associato all'azienda proprietaria del file o non è associato al file
        if (user == null)
            return false;
        else if(user.getPIva().equals(pIvaFile) || user.getPIva().equals(DBManager.getInstance().getDipendenteDao().findByCF(pIvaFile).getAzienda().getPIva()))
            return true;
        if(Utility.isConsultant(Utility.getToken(req))){
            try {
                Double.parseDouble(pIvaFile);
                return user.getPIva().equals(DBManager.getInstance().getAziendaDao().findByPIva(pIvaFile).getConsulente().getPIva());
            } catch (NumberFormatException nfe) {
                return user.getPIva().equals(DBManager.getInstance().getDipendenteDao().findByCF(pIvaFile).getAzienda().getConsulente().getPIva());
            }
        }
        return false;
    }
}
