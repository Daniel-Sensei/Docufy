package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.Consulente;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class ConsulenteService {

    @GetMapping("/consulenti")
    public List<Consulente> getAllConsulenti(){
        List <Consulente> consulenti = DBManager.getInstance().getConsulenteDao().findAll();
        return consulenti;
    }

    @GetMapping("/consulente")
    public Consulente getConsulente(@RequestParam String pIva){
        return DBManager.getInstance().getConsulenteDao().findByPIva(pIva);
    }

    @PostMapping("/aggiunta-consulente")
    public Boolean aggiuntaConsulente(@RequestBody Consulente consulente){
        return DBManager.getInstance().getConsulenteDao().insert(consulente);
    }

    @PostMapping("/eliminazione-consulente")
    public Boolean eliminazioneConsulente(@RequestParam String Piva){
        return DBManager.getInstance().getConsulenteDao().delete(Piva);
    }
}

