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
public class CorsoService {

    @GetMapping("/corsi-acquistati")
    public ResponseEntity<List<Corso>> getCorsiByAzienda(HttpServletRequest req, @RequestParam String pIva){

        User user = Utility.getRequestUser(req);

        if(user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.getCorsiByAzienda(pIva);
    }

    @GetMapping("/corsi-frequentati")
    public ResponseEntity<List<Corso>> getCorsiByEmployee(HttpServletRequest req, @RequestParam Long id){
        User user = Utility.getRequestUser(req);

        if(user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.getCorsiByDipendente(id);
    }

    @GetMapping("/corso")
    public ResponseEntity<Corso> getCorso(HttpServletRequest req, @RequestParam Long id){
        User user = Utility.getRequestUser(req);

        if(user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.getCorso(id);
    }

    @PostMapping("/aggiungi-dipendenti-corso")
    public ResponseEntity<String> aggiungiDipendentiCorso(HttpServletRequest req, @RequestParam Long idCorso, @RequestBody List<Dipendente> dipendenti){
        User user = Utility.getRequestUser(req);

        if(user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.aggiungiDipendentiCorso(idCorso, dipendenti);
    }

    @PostMapping("/aggiungi-corso")
    public ResponseEntity<String> aggiungiCorso(HttpServletRequest req, @RequestBody Corso corso, @RequestParam String pIva){

        User user = Utility.getRequestUser(req);

        if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.aggiungiCorso(corso, pIva);
    }

    @PostMapping("/modifica-corso")
    public ResponseEntity<String> modificaCorso(HttpServletRequest req, @RequestBody Corso corso, @RequestParam String pIva){

        User user = Utility.getRequestUser(req);

        if(user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.modificaCorso(corso, pIva);
    }

    @DeleteMapping("/rimuovi-corso")
    public ResponseEntity<String> rimuoviCorso(HttpServletRequest req, @RequestParam Long id){

        User user = Utility.getRequestUser(req);

        if(user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.rimuoviCorso(id);
    }
}
