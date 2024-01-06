package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.Corso;
import com.exam.esameweb24_backend.persistence.model.CorsoAcquistato;
import com.exam.esameweb24_backend.persistence.model.Documento;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class DocumentoService {

    @GetMapping("/documenti-azienda")
    public List<Documento> getDocumentiAzienda(@RequestParam String pIva){
        List<Documento> documentiAzienda = DBManager.getInstance().getDocumentoDao().findByAgency(pIva);
        return documentiAzienda;
    }

    @GetMapping("/documenti-dipendente")
    public List<Documento> getDocumentiDipendente(@RequestParam Long id){
        List<Documento> documentiDipendente = DBManager.getInstance().getDocumentoDao().findByEmployee(id);
        return documentiDipendente;
    }

    @GetMapping("/documento")
    public Documento getCorso(@RequestParam Long id){
        return DBManager.getInstance().getDocumentoDao().findById(id);
    }

    @PostMapping("/aggiunta-documento")
    public Boolean aggiuntaDocumento(@RequestBody Documento documento){
        return DBManager.getInstance().getDocumentoDao().insert(documento);
    }

    @PostMapping("/eliminazione-documento")
    public Boolean eliminazioneDocumento(@RequestParam Long id){
        return DBManager.getInstance().getDocumentoDao().delete(id);
    }
}
