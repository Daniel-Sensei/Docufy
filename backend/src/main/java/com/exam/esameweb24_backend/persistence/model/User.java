package com.exam.esameweb24_backend.persistence.model;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class User {
    protected String email;

    protected String password;

    protected String pIva;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPIva() {
        return pIva;
    }

    public void setPIva(String pIva) {
        this.pIva = pIva;
    }


    // Dipendente Service

    public ResponseEntity<List<Dipendente>> getDipendentiByPIva(String pIva){return null;}

    public ResponseEntity<Dipendente> getDipendente(Long id){return null;}

    public ResponseEntity<String> aggiungiDipendete(MultipartFile json, MultipartFile file){return null;}

    public ResponseEntity<String> modificaDipendente(MultipartFile json, MultipartFile file){return null;}

    public ResponseEntity<String> rimuoviDipendente(Long id){return null;}

}
