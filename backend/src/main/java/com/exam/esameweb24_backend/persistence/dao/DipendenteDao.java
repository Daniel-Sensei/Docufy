package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Dipendente;

import java.util.List;

public interface DipendenteDao {
    public List<Dipendente> findByAgency(String agency);
    // public List<Dipendente> findByConsultant(String consultant);  unused method to be removed before delivery if not used yet
    public Dipendente findById(Long id);
    public Dipendente findByCF(String cf);
    public List<Dipendente> findByCorsoFrequentato(Long idCorso);
    public Long insert(Dipendente dipendente);
    public boolean update(Dipendente dipendente);
    public boolean delete(Long id);

}
