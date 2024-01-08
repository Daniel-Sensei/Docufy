package com.exam.esameweb24_backend.persistence.model;

import com.exam.esameweb24_backend.persistence.DBManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class UserA extends User{

    @Override
    public ResponseEntity<List<Dipendente>> getDipendentiByPIva(String pIva) {
        if(this.pIva.equals(pIva))
            return new ResponseEntity<>(DBManager.getInstance().getDipendenteDao().findByAgency(pIva), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
