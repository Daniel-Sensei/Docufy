package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.Azienda;
import com.exam.esameweb24_backend.persistence.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class AziendaService {

    @GetMapping("/aziende")
    public ResponseEntity<List<Azienda>> getAziende(HttpServletRequest req){

        User user = Utility.getRequestUser(req);

        if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.getAziende();
    }

    @GetMapping("/azienda")
    public ResponseEntity<Azienda> getAzienda(HttpServletRequest req, @RequestParam String pIva){

        User user = Utility.getRequestUser(req);

        if (user == null) return null;

        return user.getAzienda(pIva);
    }

    @GetMapping("/profilo")
    public ResponseEntity<Azienda> getProfilo(HttpServletRequest req){

        User user = Utility.getRequestUser(req);

        if (user == null) return null;

        return user.getProfile();
    }

    @PostMapping("/aggiungi-azienda")
    public ResponseEntity<String> aggiungiAzienda(HttpServletRequest req, @RequestParam("azienda") MultipartFile json, @RequestParam("file") MultipartFile file){

        User user = Utility.getRequestUser(req);

        if (user == null) return null;

        return user.aggiungiAzienda(json, file);
    }

    @PostMapping("/modifica-azienda")
    public ResponseEntity<String> modificaAzienda(HttpServletRequest req, @RequestParam("azienda") MultipartFile json, @RequestParam("file") MultipartFile file){

        User user = Utility.getRequestUser(req);

        if (user == null) return null;

        return user.modificaAzienda(json, file);
    }

    @DeleteMapping("/rimuovi-azienda")
    public ResponseEntity<String> rimuoviAzienda(HttpServletRequest req, @RequestParam String pIva){

        User user = Utility.getRequestUser(req);

        if (user == null) return null;

        return user.rimuoviAzienda(pIva);
    }

    @PostMapping("/modifica-immagine-azienda")
    public ResponseEntity<String> modificaImmagineAzienda(HttpServletRequest req, @RequestParam String pIva,  @RequestParam("file") MultipartFile file){

        User user = Utility.getRequestUser(req);

        if (user == null) return null;

        return user.modificaImmagineAzienda(pIva, file);
    }

    @DeleteMapping("/rimuovi-immagine-azienda")
    public ResponseEntity<String> rimuoviImmagineAzienda(HttpServletRequest req, @RequestParam String pIva){

        User user = Utility.getRequestUser(req);

        if (user == null) return null;

        return user.rimuoviImmagineAzienda(pIva);
    }
}
