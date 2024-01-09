package com.exam.esameweb24_backend.persistence.model;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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


    // Azienda Service

    public ResponseEntity<List<Azienda>> getAziende(){return null;}

    public ResponseEntity<Azienda> getAzienda(String pIva){return null;}

    public ResponseEntity<Azienda> getProfile(){return null;}

    public ResponseEntity<String> aggiungiAzienda(MultipartFile json, MultipartFile file){return null;}

    public ResponseEntity<String> modificaAzienda(MultipartFile json, MultipartFile file){return null;}

    public ResponseEntity<String> rimuoviAzienda(String pIva){return null;}

    public ResponseEntity<String> modificaImmagineAzienda(String pIva, MultipartFile file){return null;}

    public ResponseEntity<String> rimuoviImmagineAzienda(String pIva){return null;}


    // Dipendente Service

    public ResponseEntity<List<Dipendente>> getDipendentiByPIva(String pIva){return null;}

    public ResponseEntity<Dipendente> getDipendente(Long id){return null;}

    public ResponseEntity<String> aggiungiDipendete(MultipartFile json, MultipartFile file){return null;}

    public ResponseEntity<String> modificaDipendente(MultipartFile json, MultipartFile file){return null;}

    public ResponseEntity<String> rimuoviDipendente(Long id){return null;}

    public ResponseEntity<String> rimuoviImmagineDipendente(Long id){return null;}


    // Documento Service

    public ResponseEntity<List<Documento>> getDocumentiAzienda(String pIva){return null;}

    public ResponseEntity<List<Documento>> getDocumentiDipendente(Long id){return null;}

    public ResponseEntity<Documento> getDocumento(Long id){return null;}

    public ResponseEntity<String> aggiungiDocumento(MultipartFile json, MultipartFile file){return null;}

    public ResponseEntity<String> modificaDocumento(MultipartFile json, MultipartFile file){return null;}

    public ResponseEntity<String> rimuoviDocumento(Long id){return null;}


    // Corso Service

    public ResponseEntity<List<Corso>> getCorsiProposti(String pIva){return null;}

    public ResponseEntity<List<Corso>> getCorsiByAzienda(String pIva){return null;}

    public ResponseEntity<List<Corso>> getCorsiByDipendente(Long id){return null;}

    public ResponseEntity<Corso> getCorso(Long id){return null;}

    public ResponseEntity<String> aggiungiCorso(Corso corso){return null;}

    public ResponseEntity<String> modificaCorso(Corso corso){return null;}

    public ResponseEntity<String> rimuoviCorso(Long id){return null;}
}
