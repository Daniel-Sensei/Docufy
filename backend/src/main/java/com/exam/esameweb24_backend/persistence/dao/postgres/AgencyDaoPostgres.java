package com.exam.esameweb24_backend.persistence.dao.postgres;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.dao.AgencyDao;
import com.exam.esameweb24_backend.persistence.model.Agency;
import com.exam.esameweb24_backend.persistence.model.Consultant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        List<Agency> agencies = new ArrayList<>();
        String query = "SELECT * FROM aziende WHERE consulente  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, consultant.getpIva());
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Agency agency = new Agency();
                agency.setpIva(rs.getString("piva"));
                agency.setRagioneSociale(rs.getString("ragionesociale"));
                agency.setEmail(rs.getString("email"));
                agency.setPhoneNumber(rs.getString("telefono"));
                agency.setAddress(rs.getString("indirizzo"));
                agency.setImagePath(rs.getString("immagine"));
                agency.setConsultant(consultant);
                agencies.add(agency);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return agencies;
    }

    @Override
    public Agency findByPIva(String pIva) {
        String query = "SELECT * FROM aziende WHERE consulente  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, consultant.getpIva());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Agency agency = new Agency();
                agency.setpIva(rs.getString("piva"));
                agency.setRagioneSociale(rs.getString("ragionesociale"));
                agency.setEmail(rs.getString("email"));
                agency.setPhoneNumber(rs.getString("telefono"));
                agency.setAddress(rs.getString("indirizzo"));
                agency.setImagePath(rs.getString("immagine"));
                agency.setConsultant(DBManager.getInstance().getConsultantDao().findByPIva(rs.getString("consulente")));
                return agency;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean insert(Agency agency) {
        String query = "INSERT INTO aziende (piva, ragionesociale, email, telefono, indirizzo, immagine, consulente) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, agency.getpIva());
            st.setString(2, agency.getRagioneSociale());
            st.setString(3, agency.getEmail());
            st.setString(4, agency.getPhoneNumber());
            st.setString(5, agency.getAddress());
            st.setString(6, agency.getImagePath());
            st.setString(7, agency.getConsultant().getpIva());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String pIva) {
        String query = "DELETE FROM aziende WHERE piva = ?";
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
