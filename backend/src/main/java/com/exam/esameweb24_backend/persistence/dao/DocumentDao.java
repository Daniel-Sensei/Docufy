package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Document;

public interface DocumentDao {
    public Document findById(Long id);
    public Document findByEmployee(Long employee);
    public Document findByAgency(String agency);
    public boolean insert(Document document);
    public boolean delete(Long id);
}
