package com.exam.esameweb24_backend.persistence.model;

import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.persistence.DBManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class UserC extends User{

    // Dipendente Service

    @Override
    public ResponseEntity<List<Dipendente>> getDipendentiByPIva(String pIva) {
        if(Utility.checkConsultantAgency(this.pIva, pIva))
            return new ResponseEntity<>(DBManager.getInstance().getDipendenteDao().findByAgency(pIva), HttpStatus.OK);
        if (this.pIva.equals(pIva))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<Dipendente> getDipendente(Long id) {
        Dipendente dipendente = DBManager.getInstance().getDipendenteDao().findById(id);
        if (dipendente==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (Utility.checkConsultantAgency(this.pIva, dipendente.getAzienda().getPIva()))
            return new ResponseEntity<>(dipendente, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> aggiungiDipendete(MultipartFile json, MultipartFile file) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> modificaDipendente(MultipartFile json, MultipartFile file) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> rimuoviDipendente(Long id) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
