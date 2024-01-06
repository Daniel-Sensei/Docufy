package com.exam.esameweb24_backend.persistence.dao.postgres;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.dao.AziendaDao;
import com.exam.esameweb24_backend.persistence.model.Azienda;
import com.exam.esameweb24_backend.persistence.model.Consulente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AziendaDaoPostgres implements AziendaDao {

    private static Connection conn = null;

    private static AziendaDaoPostgres instance = null;

    public static AziendaDaoPostgres getInstance() {
        if (instance == null) {
            instance = new AziendaDaoPostgres();
            conn = DBManager.getInstance().getConnection();
        }
        return instance;
    }

    @Override
    public List<Azienda> findByConsultant(String consultantPIva) {
        Consulente consulente = DBManager.getInstance().getConsulenteDao().findByPIva(consultantPIva);
        List<Azienda> aziende = new ArrayList<>();
        String query = "SELECT * FROM aziende WHERE consulente  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, consultantPIva);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Azienda azienda = new Azienda();
                azienda.setPIva(rs.getString("piva"));
                azienda.setRagioneSociale(rs.getString("ragionesociale"));
                azienda.setEmail(rs.getString("email"));
                azienda.setTelefono(rs.getString("telefono"));
                azienda.setIndirizzo(rs.getString("indirizzo"));
                azienda.setImg(rs.getString("immagine"));
                azienda.setConsultant(consulente);
                aziende.add(azienda);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return aziende;
    }

    @Override
    public Azienda findByPIva(String pIva) {
        String query = "SELECT * FROM aziende WHERE piva  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, pIva);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Azienda azienda = new Azienda();
                azienda.setPIva(pIva);
                azienda.setRagioneSociale(rs.getString("ragionesociale"));
                azienda.setEmail(rs.getString("email"));
                azienda.setTelefono(rs.getString("telefono"));
                azienda.setIndirizzo(rs.getString("indirizzo"));
                azienda.setImg(rs.getString("immagine"));
                azienda.setConsultant(DBManager.getInstance().getConsulenteDao().findByPIva(rs.getString("consulente")));
                return azienda;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean insert(Azienda azienda) {
        String query = "INSERT INTO aziende (piva, ragionesociale, email, telefono, indirizzo, immagine, consulente) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, azienda.getPIva());
            st.setString(2, azienda.getRagioneSociale());
            st.setString(3, azienda.getEmail());
            st.setString(4, azienda.getTelefono());
            st.setString(5, azienda.getIndirizzo());
            st.setString(6, azienda.getImg());
            st.setString(7, azienda.getConsultant().getPIva());
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