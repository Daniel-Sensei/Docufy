package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.html.HTMLHtmlElement;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class DocumentoService {

    @GetMapping("/documenti-azienda")
    public List<Documento> getDocumentiAzienda(HttpServletRequest req, @RequestParam String pIva){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);

        if (user == null) return null;

        if (!Utility.isConsultant(token)){
            List<Documento> documentiAzienda = DBManager.getInstance().getDocumentoDao().findByAgency(pIva);
            return documentiAzienda;
        }
        return null;
    }

    @GetMapping("/documenti-dipendente")
    public List<Documento> getDocumentiDipendente(HttpServletRequest req, @RequestParam Long id){
        User user = Utility.getRequestUser(req);

        if (user == null) return null;

        Dipendente dipendente = DBManager.getInstance().getDipendenteDao().findById(id);
        if (dipendente == null) return null;


        List<Documento> documentiDipendente = DBManager.getInstance().getDocumentoDao().findByEmployee(id);
        return documentiDipendente;
    }

    @GetMapping("/documento")
    public Documento getCorso(HttpServletRequest req, @RequestParam Long id){
        User user = Utility.getRequestUser(req);

        if (user == null) return null;

        return DBManager.getInstance().getDocumentoDao().findById(id);
    }

    @PostMapping("/aggiunta-documento")
    public Boolean aggiuntaDocumento(HttpServletRequest req, @RequestBody Documento documento){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);

        if (user == null) return null;

        if(!Utility.isConsultant(token)){
        return DBManager.getInstance().getDocumentoDao().insert(documento);}

        return null;
    }

    @PostMapping("/eliminazione-documento")
    public Boolean eliminazioneDocumento(HttpServletRequest req, @RequestParam Long id){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);

        if (user == null) return null;

        if(!Utility.isConsultant(token)){return DBManager.getInstance().getDocumentoDao().delete(id);}

        return null;
    }
}
