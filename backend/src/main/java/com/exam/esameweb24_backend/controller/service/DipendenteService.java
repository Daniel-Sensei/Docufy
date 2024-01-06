package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.Azienda;
import com.exam.esameweb24_backend.persistence.model.Dipendente;
import com.exam.esameweb24_backend.persistence.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class DipendenteService {

    @GetMapping("/dipendenti")
    public List<Dipendente> getDipendenti(HttpServletRequest req){
        User user = DBManager.getInstance().getUserDao().findByToken(getToken(req));
        return DBManager.getInstance().getDipendenteDao().findByAgency(user.getPIva());
    }
        // l'autenticazione è finta
        // bisogna gestire i poteri
    @GetMapping ("/dipendente")
    public Dipendente getDipendente(HttpServletRequest req, @RequestParam Long id){
        //DBManager.getInstance().getUserDao().findByToken(getToken(req));
        return DBManager.getInstance().getDipendenteDao().findById(id);
    }

    @PostMapping("/aggiungi-dipendente")
    public Boolean aggiuntaDipendete(@RequestBody Dipendente dipendente){
        return DBManager.getInstance().getDipendenteDao().insert(dipendente);
    }

    @GetMapping("/rimuovi-dipendente")
    public Boolean eliminazioneDipendente(@RequestParam Long id){
        return DBManager.getInstance().getDipendenteDao().delete(id);
    }

    private String getToken(HttpServletRequest req){
        String auth = req.getHeader("Authorization");
        return auth.substring("Basic ".length());
    }
}
