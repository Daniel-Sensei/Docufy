package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.Corso;
import com.exam.esameweb24_backend.persistence.model.CorsoAcquistato;
import com.exam.esameweb24_backend.persistence.model.CorsoFrequentato;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class CorsoService {

    @GetMapping("/corsi-porposti")
    public List<Corso> getCorsiProposti(@RequestParam String pIva){
        List<Corso> corsiProposti = DBManager.getInstance().getCorsoDao().findByConsultant(pIva);
        return corsiProposti;
    }

    @GetMapping("/corsi-acquistati")
    public List<CorsoAcquistato> getCorsiAcquistati(@RequestParam String pIva){
        List<CorsoAcquistato> corsiAcquistati = DBManager.getInstance().getCorsoDao().findByAgency(pIva);
        return corsiAcquistati;
    }

    @GetMapping("/corsi-frequentati")
    public List<CorsoFrequentato> getCorsoFrequentati(@RequestParam Long id){
        List<CorsoFrequentato> corsoFrequentati = DBManager.getInstance().getCorsoDao().findByEmployee(id);
        return corsoFrequentati;
    }

    @GetMapping("/corso")
    public Corso getCorso(@RequestParam Long id){
        return DBManager.getInstance().getCorsoDao().findById(id);
    }

    @PostMapping("/aggiunta-corso")
    public Boolean aggiuntaCorso(@RequestBody Corso corso){
        return DBManager.getInstance().getCorsoDao().insert(corso);
    }

    @PostMapping("/eliminazione-corso")
    public Boolean eliminazioneCorso(@RequestParam Long id){
        return DBManager.getInstance().getCorsoDao().delete(id);
    }

    private String getToken(HttpServletRequest req){
        String auth = req.getHeader("Authorization");
        return auth.substring("Basic ".length());
    }
}
