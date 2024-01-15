package com.exam.esameweb24_backend.persistence.model;

import java.util.ArrayList;

public class Ricerca {

    private ArrayList<Azienda> aziende;
    private ArrayList<Documento> documenti;
    private ArrayList<Dipendente> dipendenti;

    public Ricerca() {
        aziende = new ArrayList<>();
        documenti = new ArrayList<>();
        dipendenti = new ArrayList<>();
    }

    public Ricerca(ArrayList<Azienda> aziende, ArrayList<Documento> documenti, ArrayList<Dipendente> dipendenti) {
        this.aziende = aziende;
        this.documenti = documenti;
        this.dipendenti = dipendenti;
    }

    public ArrayList<Azienda> getAziende() {
        return aziende;
    }

    public Azienda getAzienda(int i) {
        return aziende.get(i);
    }

    public void addToAziende(Azienda azienda) {
        aziende.add(azienda);
    }
    public void setAziende(ArrayList<Azienda> aziende) {
        this.aziende = aziende;
    }

    public ArrayList<Documento> getDocumenti() {
        return documenti;
    }

    public Documento getDocumento(int i) {
        return documenti.get(i);
    }

    public void addToDocumenti(Documento documento) {
        documenti.add(documento);
    }

    public void setDocumenti(ArrayList<Documento> documenti) {
        this.documenti = documenti;
    }

    public ArrayList<Dipendente> getDipendenti() {
        return dipendenti;
    }

    public Dipendente getDipendente(int i) {
        return dipendenti.get(i);
    }

    public void addToDipendenti(Dipendente dipendente) {
        dipendenti.add(dipendente);
    }

    public void setDipendenti(ArrayList<Dipendente> dipendenti) {
        this.dipendenti = dipendenti;
    }
}
