package com.exam.esameweb24_backend.persistence.dao.postgres;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.dao.DocumentDao;
import com.exam.esameweb24_backend.persistence.model.Agency;
import com.exam.esameweb24_backend.persistence.model.Document;
import com.exam.esameweb24_backend.persistence.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<Document> findByEmployee(Long employeeID) {
        Employee employee = DBManager.getInstance().getEmployeeDao().findById(employeeID);
        List<Document> documents = new ArrayList<>();
        String query = "SELECT * FROM documenti WHERE dipendente  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, employeeID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Document document = new Document();
                document.setId(rs.getLong("id"));
                document.setName(rs.getString("nome"));
                document.setUrl(rs.getString("url"));
                document.setReleaseDate(rs.getDate("data_rilascio"));
                document.setExpirationDate(rs.getDate("data_scadenza"));
                document.setEmployee(employee);
                document.setState(rs.getString("stato"));
                document.setFormat(rs.getString("formato"));
                documents.add(document);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return documents;
    }

    @Override
    public List<Document> findByAgency(String agencyPIva) {
        Agency agency = DBManager.getInstance().getAgencyDao().findByPIva(agencyPIva);
        List<Document> documents = new ArrayList<>();
        String query = "SELECT * FROM documenti WHERE agenzia  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, agencyPIva);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Document document = new Document();
                document.setId(rs.getLong("id"));
                document.setName(rs.getString("nome"));
                document.setUrl(rs.getString("url"));
                document.setReleaseDate(rs.getDate("data_rilascio"));
                document.setExpirationDate(rs.getDate("data_scadenza"));
                document.setAgency(agency);
                document.setState(rs.getString("stato"));
                document.setFormat(rs.getString("formato"));
                documents.add(document);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return documents;
    }

    @Override
    public Document findById(Long id) {
        String query = "SELECT * FROM documenti WHERE id  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Document document = new Document();
                document.setId(rs.getLong("id"));
                document.setName(rs.getString("nome"));
                document.setUrl(rs.getString("url"));
                document.setReleaseDate(rs.getDate("data_rilascio"));
                document.setExpirationDate(rs.getDate("data_scadenza"));
                if(rs.getInt("dipendente") != 0)
                    document.setEmployee(DBManager.getInstance().getEmployeeDao().findById(rs.getLong("dipendente")));
                if(rs.getString("agenzia") != null)
                    document.setAgency(DBManager.getInstance().getAgencyDao().findByPIva(rs.getString("agenzia")));
                document.setState(rs.getString("stato"));
                document.setFormat(rs.getString("formato"));
                return document;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean insert(Document document) {
        String query = "INSERT INTO documenti (nome, url, data_rilascio, data_scadenza, dipendente, agenzia, stato, formato) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, document.getName());
            st.setString(2, document.getUrl());
            st.setDate(3, (Date) document.getReleaseDate());
            st.setDate(4, (Date) document.getExpirationDate());
            if(document.getEmployee() != null) {
                st.setLong(5, document.getEmployee().getId());
                st.setNull(6, java.sql.Types.VARCHAR);
            } else {
                st.setNull(5, java.sql.Types.INTEGER);
                if (document.getAgency() != null)
                    st.setString(6, document.getAgency().getpIva());
                else
                    return false;
            }
            st.setString(7, document.getState());
            st.setString(8, document.getFormat());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM documenti WHERE id = ?";
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
