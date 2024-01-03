package com.exam.esameweb24_backend.persistence.dao.postgres;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.dao.ConsultantDao;
import com.exam.esameweb24_backend.persistence.model.Consultant;

import java.sql.Connection;
import java.util.List;

public class ConsultantDaoPostgres implements ConsultantDao {

    private static Connection conn = null;

    private static ConsultantDaoPostgres instance = null;

    public static ConsultantDaoPostgres getInstance() {
        if (instance == null) {
            instance = new ConsultantDaoPostgres();
            conn = DBManager.getInstance().getConnection();
        }
        return instance;
    }

    @Override
    public List<Consultant> findAll() {
        return null;
    }

    @Override
    public Consultant findByPIva(String pIva) {
        return null;
    }

    @Override
    public boolean insert(Consultant consultant) {
        return false;
    }

    @Override
    public boolean delete(String pIva) {
        return false;
    }
}
