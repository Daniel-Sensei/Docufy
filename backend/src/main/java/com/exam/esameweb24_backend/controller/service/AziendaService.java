package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.Azienda;
import com.exam.esameweb24_backend.persistence.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class AziendaService {

    @GetMapping("/aziende")
    public List<Azienda> getAziende(HttpServletRequest req){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);

        if (user == null) return null;

        if (Utility.isConsultant(token)) {
            List <Azienda> aziende = DBManager.getInstance().getAziendaDao().findByConsultant(user.getPIva());
            return aziende;
        }
        return null;
    }

    @GetMapping("/azienda")
    public Azienda getAzienda(@RequestParam String pIva, HttpServletRequest req){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);

        if (user == null) return null;

        return DBManager.getInstance().getAziendaDao().findByPIva(pIva);
    }

    @PostMapping("/aggiunta-azienda")
    public Boolean aggiuntaAzienda(@RequestBody Azienda azienda, HttpServletRequest req){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);

        if (user == null) return null;

        if(Utility.isConsultant(token)) {
            return DBManager.getInstance().getAziendaDao().insert(azienda);
        }

        return null;
    }

    @PostMapping("/elimina-azienda")
    public Boolean eliminazioneAzienda(@RequestParam String pIva, HttpServletRequest req){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);

        if (user == null) return null;

        if(Utility.isConsultant(token)) {
            return DBManager.getInstance().getAziendaDao().delete(pIva);
        }
        return null;
    }
}
