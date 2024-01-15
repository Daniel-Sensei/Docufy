package com.exam.esameweb24_backend.persistence.model;

public class Email {

    private String nome;

    private String mail;

    private String oggetto;

    private String messaggio;

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getMail() {return mail;}

    public void setMail(String mail) {this.mail = mail;}

    public String getOggetto() {return oggetto;}

    public void setOggetto(String oggetto) {this.oggetto = oggetto;}

    public String getMessaggio() {return messaggio;}

    public void setMessaggio(String messaggio) {this.messaggio = messaggio;}
}
