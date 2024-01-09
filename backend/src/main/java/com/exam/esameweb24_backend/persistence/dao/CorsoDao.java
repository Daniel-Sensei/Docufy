package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Corso;
import com.exam.esameweb24_backend.persistence.model.CorsoFrequentato;
import com.exam.esameweb24_backend.persistence.model.CorsoAcquistato;

import java.util.List;

public interface CorsoDao {
    public List<Corso> findByConsultant(String consultant);
    public List<CorsoAcquistato> findByAgency(String agency);
    public List<CorsoFrequentato> findByEmployee(Long employee);
    public Corso findById(Long id);
    public Long insert(Corso corso);
    public boolean update(Corso corso);
    public boolean delete(Long id);
}
