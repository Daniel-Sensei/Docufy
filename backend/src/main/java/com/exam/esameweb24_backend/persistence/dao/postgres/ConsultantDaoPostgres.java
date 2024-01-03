package com.exam.esameweb24_backend.persistence.dao.postgres;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.dao.ConsultantDao;
import com.exam.esameweb24_backend.persistence.model.Consultant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        List<Consultant> consultants = new ArrayList<>();
        String query = "SELECT * FROM consulenti";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Consultant consultant = new Consultant();
                consultant.setpIva(rs.getString("piva"));
                consultant.setRagioneSociale(rs.getString("ragionesociale"));
                consultant.setEmail(rs.getString("email"));
                consultant.setPhoneNumber(rs.getString("telefono"));
                consultant.setAddress(rs.getString("indirizzo"));
                consultant.setImagePath(rs.getString("immagine"));
                consultants.add(consultant);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return consultants;
    }

    @Override
    public Consultant findByPIva(String pIva) {
        String query = "SELECT * FROM consulenti WHERE piva = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, pIva);
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                Consultant consultant = new Consultant();
                consultant.setpIva(rs.getString("piva"));
                consultant.setRagioneSociale(rs.getString("ragionesociale"));
                consultant.setEmail(rs.getString("email"));
                consultant.setPhoneNumber(rs.getString("telefono"));
                consultant.setAddress(rs.getString("indirizzo"));
                consultant.setImagePath(rs.getString("immagine"));
                return consultant;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean insert(Consultant consultant) {
        String query = "INSERT INTO consulenti (piva, ragionesociale, email, telefono, indirizzo, immagine) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, consultant.getpIva());
            st.setString(2, consultant.getRagioneSociale());
            st.setString(3, consultant.getEmail());
            st.setString(4, consultant.getPhoneNumber());
            st.setString(5, consultant.getAddress());
            st.setString(6, consultant.getImagePath());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String pIva) {
        String query = "DELETE FROM consulenti WHERE piva = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, pIva);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
