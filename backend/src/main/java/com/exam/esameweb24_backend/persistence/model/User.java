package com.exam.esameweb24_backend.persistence.model;

import org.springframework.http.ResponseEntity;

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

}
