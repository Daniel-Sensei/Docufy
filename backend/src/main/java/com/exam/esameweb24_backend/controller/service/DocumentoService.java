package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class DocumentoService {

    @GetMapping("/documenti-azienda")
    public ResponseEntity<List<Documento>> getDocumentiAzienda(HttpServletRequest req, @RequestParam String pIva){

        User user = Utility.getRequestUser(req);

        if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.getDocumentiAzienda(pIva);
    }

    @GetMapping("/documenti-dipendente")
    public ResponseEntity<List<Documento>> getDocumentiDipendente(HttpServletRequest req, @RequestParam Long id){

        User user = Utility.getRequestUser(req);

        if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.getDocumentiDipendente(id);
    }

    @GetMapping("/documento")
    public ResponseEntity<Documento>  getDocumento(HttpServletRequest req, @RequestParam Long id){

        User user = Utility.getRequestUser(req);

        if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.getDocumento(id);
    }

    @PostMapping("/aggiungi-documento")
    public ResponseEntity<String> aggiungiDocumento(HttpServletRequest req, @RequestParam("documento") MultipartFile json, @RequestParam("file") MultipartFile file){

        User user = Utility.getRequestUser(req);

        if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.aggiungiDocumento(json, file);
    }

    @PostMapping("/modifica-documento")
    public ResponseEntity<String> modificaDocumento(HttpServletRequest req, @RequestParam("documento") MultipartFile json, @RequestParam("file") MultipartFile file){

        User user = Utility.getRequestUser(req);

        if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.modificaDocumento(json, file);
    }

    @DeleteMapping("/rimuovi-documento")
    public ResponseEntity<String> rimuoviDocumento(HttpServletRequest req, @RequestParam Long id){

        User user = Utility.getRequestUser(req);

        if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.rimuoviDocumento(id);
    }
}
