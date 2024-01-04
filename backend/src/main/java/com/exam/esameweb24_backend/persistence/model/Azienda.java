package com.exam.esameweb24_backend.persistence.model;

public class Azienda {
    private String pIva;

    private String email;

    private String telefono;

    private String ragioneSociale;

    private String indirizzo;

    private String img;

    private Consulente consulente;

    public String getpIva() {
        return pIva;
    }

    public void setpIva(String pIva) {
        this.pIva = pIva;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRagioneSociale() {
        return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Consulente getConsultant() {
        return consulente;
    }

    public void setConsultant(Consulente consulente) {
        this.consulente = consulente;
    }
}
