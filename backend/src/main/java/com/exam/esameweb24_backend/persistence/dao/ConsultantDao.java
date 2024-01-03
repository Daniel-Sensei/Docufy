package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Consultant;

import java.util.List;

public interface ConsultantDao {
    public List<Consultant> findAll();
    public Consultant findByPIva(String pIva);
    public boolean insert(Consultant consultant);
    public boolean delete(String pIva);
}
