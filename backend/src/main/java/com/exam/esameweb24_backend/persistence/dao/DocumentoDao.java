package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Documento;

import java.util.List;

public interface DocumentoDao {
    public List<Documento> findByEmployee(Long employee);
    public List<Documento> findByAgency(String agency);
    public Documento findById(Long id);
    public boolean insert(Documento documento);
    public boolean delete(Long id);
}
