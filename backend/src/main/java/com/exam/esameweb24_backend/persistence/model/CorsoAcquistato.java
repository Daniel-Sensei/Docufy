package com.exam.esameweb24_backend.persistence.model;

import java.util.Date;

public class CorsoAcquistato {
    private Corso corso;
    private Azienda azienda;
    private Date date;

    public Corso getCourse() {
        return corso;
    }

    public void setCourse(Corso corso) {
        this.corso = corso;
    }

    public Azienda getAgency() {
        return azienda;
    }

    public void setAgency(Azienda azienda) {
        this.azienda = azienda;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
