package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Documento;

import java.util.ArrayList;
import java.util.List;

public interface DocumentoDao {
    public List<Documento> getAll();
    public List<Documento> findByDipendente(Long employee);
    public List<Documento> findByAzienda(String agency);
    public Documento findById(Long id);
    public ArrayList<Documento> ricerca (List<String> q);
    public Long insert(Documento documento);
    public boolean update(Documento documento);
    public boolean delete(Long id);
}
