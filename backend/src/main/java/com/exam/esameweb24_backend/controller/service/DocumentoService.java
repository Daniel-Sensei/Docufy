package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.controller.Utility;
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

    @PostMapping("/aggiungi-documento-Azienda")
    public ResponseEntity<String> aggiungiDocumentoAzienda(HttpServletRequest req, @RequestParam("documento") MultipartFile json, @RequestParam("file") MultipartFile file, @RequestParam String pIva){

        User user = Utility.getRequestUser(req);

        if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.aggiungiDocumentoAzienda(json, file, pIva);
    }

    @PostMapping("/aggiungi-documento-dipendente")
    public ResponseEntity<String> aggiungiDocumentoDipendente(HttpServletRequest req, @RequestParam("documento") MultipartFile json, @RequestParam("file") MultipartFile file, @RequestParam String cf){

        User user = Utility.getRequestUser(req);

        if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.aggiungiDocumentoDipendente(json, file, cf);
    }

    @PostMapping("/modifica-documento-azienda")
    public ResponseEntity<String> modificaDocumentoAzienda(HttpServletRequest req, @RequestParam("documento") MultipartFile json, @RequestParam("file") MultipartFile file, @RequestParam String pIva){

        User user = Utility.getRequestUser(req);

        if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.modificaDocumentoAzienda(json, file, pIva);
    }

    @PostMapping("/modifica-documento-dipendente")
    public ResponseEntity<String> modificaDocumentoDipendente(HttpServletRequest req, @RequestParam("documento") MultipartFile json, @RequestParam("file") MultipartFile file, @RequestParam String cf){

        User user = Utility.getRequestUser(req);

        if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.modificaDocumentoDipendente(json, file, cf);
    }

    @DeleteMapping("/rimuovi-documento")
    public ResponseEntity<String> rimuoviDocumento(HttpServletRequest req, @RequestParam Long id){

        User user = Utility.getRequestUser(req);

        if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.rimuoviDocumento(id);
    }
}
