package com.exam.esameweb24_backend.persistence.dao.postgres;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.dao.DocumentDao;
import com.exam.esameweb24_backend.persistence.model.Document;

import java.sql.Connection;

public class DocumentDaoPostgres implements DocumentDao {

    private static Connection conn = null;

    private static DocumentDaoPostgres instance = null;

    public static DocumentDaoPostgres getInstance() {
        if (instance == null) {
            instance = new DocumentDaoPostgres();
            conn = DBManager.getInstance().getConnection();
        }
        return instance;
    }

    @Override
    public Document findById(Long id) {
        return null;
    }

    @Override
    public Document findByEmployee(Long employee) {
        return null;
    }

    @Override
    public Document findByAgency(String agency) {
        return null;
    }

    @Override
    public boolean insert(Document document) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
