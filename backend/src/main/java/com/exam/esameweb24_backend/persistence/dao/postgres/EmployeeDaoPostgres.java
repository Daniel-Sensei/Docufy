package com.exam.esameweb24_backend.persistence.dao.postgres;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.dao.EmployeeDao;
import com.exam.esameweb24_backend.persistence.model.Employee;

import java.sql.Connection;

public class EmployeeDaoPostgres implements EmployeeDao{

    private static Connection conn = null;

    private static EmployeeDaoPostgres instance = null;

    public static EmployeeDaoPostgres getInstance() {
        if (instance == null) {
            instance = new EmployeeDaoPostgres();
            conn = DBManager.getInstance().getConnection();
        }
        return instance;
    }

    @Override
    public Employee findById(Long id) {
        return null;
    }

    @Override
    public Employee findByAgency(String agency) {
        return null;
    }

    @Override
    public boolean insert(Employee employee) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
