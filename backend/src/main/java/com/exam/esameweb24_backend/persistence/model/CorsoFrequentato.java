package com.exam.esameweb24_backend.persistence.model;

import java.util.Date;

public class CorsoFrequentato {
    private Corso corso;
    private Dipendente dipendente;
    private Date startingDate;
    private Date endingDate;

    public Corso getCourse() {
        return corso;
    }

    public void setCourse(Corso corso) {
        this.corso = corso;
    }

    public Dipendente getEmployee() {
        return dipendente;
    }

    public void setEmployee(Dipendente dipendente) {
        this.dipendente = dipendente;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }
}
