package com.exam.esameweb24_backend.persistence.dao.postgres;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.dao.EmployeeDao;
import com.exam.esameweb24_backend.persistence.model.Agency;
import com.exam.esameweb24_backend.persistence.model.Document;
import com.exam.esameweb24_backend.persistence.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public List<Employee> findByAgency(String agencyPIva) {
        Agency agency = DBManager.getInstance().getAgencyDao().findByPIva(agencyPIva);
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM dipendenti WHERE agenzia  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, agencyPIva);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getLong("id"));
                employee.setCF(rs.getString("cf"));
                employee.setName(rs.getString("nome"));
                employee.setSurname(rs.getString("cognome"));
                employee.setBirthDate(rs.getDate("data_nascita"));
                employee.setEmail(rs.getString("email"));
                employee.setPhoneNumber(rs.getString("telefono"));
                employee.setResidence(rs.getString("indirizzo"));
                employee.setAgency(agency);
                employee.setHireDate(rs.getDate("data_assunzione"));
                employee.setRole(rs.getString("ruolo"));
                employee.setImagePath(rs.getString("foto"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }

    @Override
    public Employee findById(Long id) {
        String query = "SELECT * FROM dipendenti WHERE id  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getLong("id"));
                employee.setCF(rs.getString("cf"));
                employee.setName(rs.getString("nome"));
                employee.setSurname(rs.getString("cognome"));
                employee.setBirthDate(rs.getDate("data_nascita"));
                employee.setEmail(rs.getString("email"));
                employee.setPhoneNumber(rs.getString("telefono"));
                employee.setResidence(rs.getString("indirizzo"));
                employee.setAgency(DBManager.getInstance().getAgencyDao().findByPIva(rs.getString("azienda")));
                employee.setHireDate(rs.getDate("data_assunzione"));
                employee.setRole(rs.getString("ruolo"));
                employee.setImagePath(rs.getString("foto"));
                return employee;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean insert(Employee employee) {
        String query = "INSERT INTO dipendenti (cf, nome, cognome, data_nascita, email, telefono, indirizzo, azienda, data_assunzione, ruolo, foto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, employee.getCF());
            st.setString(2, employee.getName());
            st.setString(3, employee.getSurname());
            st.setDate(4, new java.sql.Date(employee.getBirthDate().getTime()));
            st.setString(5, employee.getEmail());
            st.setString(6, employee.getPhoneNumber());
            st.setString(7, employee.getResidence());
            st.setString(8, employee.getAgency().getpIva());
            st.setDate(9, new java.sql.Date(employee.getHireDate().getTime()));
            st.setString(10, employee.getRole());
            st.setString(11, employee.getImagePath());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM dipendenti WHERE id = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
