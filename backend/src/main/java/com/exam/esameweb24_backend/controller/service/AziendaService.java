package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.Azienda;
import com.exam.esameweb24_backend.persistence.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class AziendaService {

    //@GetMapping("/aziende")
    //public List<Azienda> geetAziende(HttpServletRequest req){}

    @GetMapping("/azienda")
    public Azienda getAzienda(@RequestParam String pIva){
        return DBManager.getInstance().getAziendaDao().findByPIva(pIva);
    }

    @PostMapping("/aggiunta-azienda")
    public Boolean aggiuntaAzienda(@RequestBody Azienda azienda){
        return DBManager.getInstance().getAziendaDao().insert(azienda);
    }

    @PostMapping("/elimina-azienda")
    public Boolean eliminazioneAzienda(@RequestParam String pIva){
        return DBManager.getInstance().getAziendaDao().delete(pIva);
    }

    private String getToken(HttpServletRequest req){
        String auth = req.getHeader("Authorization");
        return auth.substring("Basic ".length());
    }
}
