package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Dipendente;

import java.util.List;

public interface DipendenteDao {
    public List<Dipendente> findByAgency(String agency);
    public List<Dipendente> findByConsultant(String consultant);
    public Dipendente findById(Long id);
    public Long insert(Dipendente dipendente);
    public boolean update(Dipendente dipendente);
    public boolean delete(Long id);

}
