package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Dipendente;

import java.util.List;

public interface DipendenteDao {
    public List<Dipendente> findByAgency(String agency);
    public Dipendente findById(Long id);
    public boolean insert(Dipendente dipendente);
    public boolean delete(Long id);
}
