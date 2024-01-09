package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Corso;

import java.util.List;

public interface CorsoDao {
    public List<Corso> findByConsultant(String consultant);
    public List<Corso> findByAgency(String agency);
    public List<Corso> findByEmployee(Long employee);
    public Corso findById(Long id);
    public Long insert(Corso corso);
    public boolean update(Corso corso);
    public boolean delete(Long id);
    public boolean addDipendente(Long idCorso, Long idDipendente);
    public boolean addAzienda(Long idCorso, String pIva);
}
