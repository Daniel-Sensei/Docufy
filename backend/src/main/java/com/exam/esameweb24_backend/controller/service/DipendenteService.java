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
public class DipendenteService {

    // Questo servizio fornisce i soli dipendenti associati alla partita iva passata come parametro
    // Il suo utilizzo è riservato al consulente associato a quella P.Iva
    @GetMapping("/dipendenti")
    public ResponseEntity<List<Dipendente>> getDipendentiByPIva(HttpServletRequest req, @RequestParam String pIva){

        User user = Utility.getRequestUser(req);

        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (user==null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.getDipendentiByPIva(pIva);
    }

    // Questo servizio restituisce il dipendente con l'id passato come parametro
    // Il suo utilizzo è riservato all'azienda per cui lavora il dipendente
    // e al consulente associato a quella P.Iva
    @GetMapping ("/dipendente")
    public ResponseEntity<Dipendente> getDipendente(HttpServletRequest req, @RequestParam Long id){

        User user = Utility.getRequestUser(req);

        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (user==null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.getDipendente(id);
    }

    // Questo servizio permette di aggiungere un dipendente
    // Il suo utilizzo è riservato all'azienda per cui lavora il dipendente
    @PostMapping("/aggiungi-dipendente")
    public ResponseEntity<String> aggiungiDipendete(HttpServletRequest req, @RequestParam("dipendente") MultipartFile json, @RequestParam("file") MultipartFile file){

        User user = Utility.getRequestUser(req);

        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (user==null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.aggiungiDipendete(json, file);
    }

    // Questo servizio permette di modificare un dipendente
    // Il suo utilizzo è riservato all'azienda per cui lavora il dipendente
    @PostMapping("/modifica-dipendente")
    public ResponseEntity<String> modificaDipendente(HttpServletRequest req, @RequestParam("dipendente") MultipartFile json, @RequestParam("file") MultipartFile file){

        User user = Utility.getRequestUser(req);

        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (user==null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.modificaDipendente(json, file);
    }

    // Questo servizio permette di eliminare un dipendente
    // Il suo utilizzo è riservato all'azienda per cui lavora il dipendente
    @GetMapping("/rimuovi-dipendente")
    public ResponseEntity<String> rimuoviDipendente(HttpServletRequest req, @RequestParam Long id){

        User user = Utility.getRequestUser(req);

        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (user==null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.rimuoviDipendente(id);
    }
}
