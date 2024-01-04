package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Azienda;

import java.util.List;

public interface AziendaDao {
    public List<Azienda> findByConsultant(String consultant);
    public Azienda findByPIva(String pIva);
    public boolean insert(Azienda azienda);
    public boolean delete(String pIva);
}
