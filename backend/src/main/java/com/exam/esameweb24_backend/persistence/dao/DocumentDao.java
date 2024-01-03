package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Document;

import java.util.List;

public interface DocumentDao {
    public List<Document> findByEmployee(Long employee);
    public List<Document> findByAgency(String agency);
    public Document findById(Long id);
    public boolean insert(Document document);
    public boolean delete(Long id);
}
