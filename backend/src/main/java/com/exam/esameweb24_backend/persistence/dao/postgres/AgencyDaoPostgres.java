package com.exam.esameweb24_backend.persistence.dao.postgres;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.dao.AgencyDao;
import com.exam.esameweb24_backend.persistence.model.Agency;
import com.exam.esameweb24_backend.persistence.model.Consultant;

import java.sql.Connection;
import java.util.List;

public class AgencyDaoPostgres implements AgencyDao {

    private static Connection conn = null;

    private static AgencyDaoPostgres instance = null;

    public static AgencyDaoPostgres getInstance() {
        if (instance == null) {
            instance = new AgencyDaoPostgres();
            conn = DBManager.getInstance().getConnection();
        }
        return instance;
    }

    @Override
    public List<Agency> findByConsultant(Consultant consultant) {
        return null;
    }

    @Override
    public Agency findByPIva(String pIva) {
        return null;
    }

    @Override
    public boolean insert(Agency agency) {
        return false;
    }

    @Override
    public boolean delete(String pIva) {
        return false;
    }
}
