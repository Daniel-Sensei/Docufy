package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Consulente;

import java.util.List;

public interface ConsulenteDao {
    public Consulente findByPIva(String pIva);
    public boolean insert(Consulente consulente);
    public boolean update(Consulente consulente);
    public boolean delete(String pIva);
}
