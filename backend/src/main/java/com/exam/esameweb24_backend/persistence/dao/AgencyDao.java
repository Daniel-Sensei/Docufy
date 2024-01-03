package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Agency;

import java.util.List;

public interface AgencyDao {
    public List<Agency> findByConsultant(String consultant);
    public Agency findByPIva(String pIva);
    public boolean insert(Agency agency);
    public boolean delete(String pIva);
}
