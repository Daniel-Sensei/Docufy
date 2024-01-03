package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Agency;
import com.exam.esameweb24_backend.persistence.model.Consultant;

import java.util.List;

public interface AgencyDao {
    public List<Agency> findByConsultant(Consultant consultant);
    public Agency findByPIva(String pIva);
    public boolean insert(Agency agency);
    public boolean delete(String pIva);
}
