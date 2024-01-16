package com.exam.esameweb24_backend.persistence.model;

public class Corso {
    private Long id;

    private String nome;

    private Double prezzo;

    private String descrizione;

    private Integer durata;

    private Consulente consulente;

    private String categoria;

    private Integer posti;

    private Integer postiDisponibili;

    private Boolean esameFinale;

    private Azienda azienda;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getDurata() {
        return durata;
    }

    public void setDurata(Integer durata) {
        this.durata = durata;
    }

    public Consulente getConsulente() {
        return consulente;
    }

    public void setConsulente(Consulente consulente) {
        this.consulente = consulente;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getPosti() {
        return posti;
    }

    public void setPosti(Integer posti) {
        this.posti = posti;
    }

    public Integer getPostiDisponibili() {
        return postiDisponibili;
    }

    public void setPostiDisponibili(Integer postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
    }

    public Boolean isFinalExam() {
        return esameFinale;
    }

    public void setEsameFinale(Boolean esameFinale) {
        this.esameFinale = esameFinale;
    }

    public Azienda getAzienda() {
    	return azienda;
    }

    public void setAzienda(Azienda azienda) {
    	this.azienda = azienda;
    }
}
