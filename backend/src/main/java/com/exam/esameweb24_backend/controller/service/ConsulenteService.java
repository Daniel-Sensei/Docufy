package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.Consulente;
import com.exam.esameweb24_backend.persistence.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jshell.execution.Util;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class ConsulenteService {

    @GetMapping("/consulenti")
    public List<Consulente> getAllConsulenti(HttpServletRequest req){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);

        if(user == user) return null;

        if(!Utility.isConsultant(token)) {
            List<Consulente> consulenti = DBManager.getInstance().getConsulenteDao().findAll();
            return consulenti;
        }

        return null;
    }

    @GetMapping("/consulente")
    public Consulente getConsulente(HttpServletRequest req, @RequestParam String pIva){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);

        if(user == null) return null;

        if(!Utility.isConsultant(token)){return DBManager.getInstance().getConsulenteDao().findByPIva(pIva);}

        return null;
    }

    @PostMapping("/aggiunta-consulente")
    public Boolean aggiuntaConsulente(HttpServletRequest req, @RequestBody Consulente consulente){
        String token = Utility.getToken(req);
        return DBManager.getInstance().getConsulenteDao().insert(consulente);
    }

    @PostMapping("/eliminazione-consulente")
    public Boolean eliminazioneConsulente(@RequestParam String Piva){
        return DBManager.getInstance().getConsulenteDao().delete(Piva);
    }
}

