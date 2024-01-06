package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.controller.Utility;
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
import java.util.UUID;

@RestController
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadFile(HttpServletRequest req, @RequestParam("file") MultipartFile file) {

        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (Utility.getRequestUser(req) == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if (file.isEmpty()) {
            return new ResponseEntity<>("Nessun file selezionato", HttpStatus.BAD_REQUEST);
        }

        try {
            String fileName = generateUniqueFileName(file);
            String filePath = uploadDir + File.separator + fileName;
            Path destination = Paths.get(filePath);

            Files.write(destination, file.getBytes());

            return new ResponseEntity<>(filePath, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>("Errore durante il caricamento del file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-file")
    public ResponseEntity<Resource> getFile(HttpServletRequest req, @RequestParam("path") String path) {

        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (Utility.getRequestUser(req) == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

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

    private String generateUniqueFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        assert originalFileName != null;
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID() + extension;
    }
}
