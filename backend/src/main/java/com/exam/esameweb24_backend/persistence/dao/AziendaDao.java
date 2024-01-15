package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Azienda;

import java.util.ArrayList;
import java.util.List;

public interface AziendaDao {
    public List<Azienda> findByConsultant(String consultant);
    public Azienda findByPIva(String pIva);
    public List<Azienda> findByCorsoAcquistato(Long idCorso);
    public ArrayList<Azienda> ricerca (List<String> q);
    public boolean insert(Azienda azienda);
    public boolean update(Azienda azienda);
    public boolean delete(String pIva);
}
