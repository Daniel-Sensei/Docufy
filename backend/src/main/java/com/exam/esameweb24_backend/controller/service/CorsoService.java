package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.UpgradeToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class CorsoService {

    @GetMapping("/corsi-porposti")
    public List<Corso> getCorsiProposti(HttpServletRequest req, @RequestParam String pIva){
        User user = Utility.getRequestUser(req);

        if(user == null) return null;

            List<Corso> corsiProposti = DBManager.getInstance().getCorsoDao().findByConsultant(pIva);
            return corsiProposti;
    }

    @GetMapping("/corsi-acquistati")
    public List<CorsoAcquistato> getCorsiAcquistati(HttpServletRequest req, @RequestParam String pIva){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);

        if(user == null) return null;

        if(!Utility.isConsultant(token)) {
            List<CorsoAcquistato> corsiAcquistati = DBManager.getInstance().getCorsoDao().findByAgency(pIva);
            return corsiAcquistati;
        }
        return null;
    }

    @GetMapping("/corsi-frequentati")
    public List<CorsoFrequentato> getCorsoFrequentati(HttpServletRequest req, @RequestParam Long id){
        User user = Utility.getRequestUser(req);

        if(user == null) return null;

        Dipendente dipendente = DBManager.getInstance().getDipendenteDao().findById(id);
        if(dipendente == null) return null;

        List<CorsoFrequentato> corsiFrequentati = DBManager.getInstance().getCorsoDao().findByEmployee(id);
        return corsiFrequentati;
    }

    @GetMapping("/corso")
    public Corso getCorso(HttpServletRequest req, @RequestParam Long id){
        User user = Utility.getRequestUser(req);

        if(user == null) return null;

        return DBManager.getInstance().getCorsoDao().findById(id);
    }

    @PostMapping("/aggiunta-corso")
    public Boolean aggiuntaCorso(HttpServletRequest req, @RequestBody Corso corso){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);

        if (user == null) return null;

        if(Utility.isConsultant(token)) {
            return DBManager.getInstance().getCorsoDao().insert(corso);
        }

        return null;
    }

    @PostMapping("/eliminazione-corso")
    public Boolean eliminazioneCorso(HttpServletRequest req, @RequestParam Long id){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);

        if(user == null) return null;

        if(Utility.isConsultant(token)) {
            return DBManager.getInstance().getCorsoDao().delete(id);
        }

        return null;
    }
}
