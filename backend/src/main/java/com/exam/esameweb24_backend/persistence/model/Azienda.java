package com.exam.esameweb24_backend.persistence.model;

import java.util.Objects;

public class Azienda {

    public Azienda() {}

    private String pIva;

    private String email;

    private String telefono;

    private String ragioneSociale;

    private String indirizzo;

    private String img;

    private Consulente consulente;

    public String getPIva() {
        return pIva;
    }

    public void setPIva(String pIva) {
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

    public Consulente getConsulente() {
        return consulente;
    }

    public void setConsulente(Consulente consulente) {
        this.consulente = consulente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Azienda azienda = (Azienda) o;
        return Objects.equals(pIva, azienda.pIva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pIva);
    }
}
