package com.exam.esameweb24_backend.persistence.dao.postgres;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.dao.ConsulenteDao;
import com.exam.esameweb24_backend.persistence.model.Consulente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsulenteDaoPostgres implements ConsulenteDao {

    private static Connection conn = null;

    private static ConsulenteDaoPostgres instance = null;

    public static ConsulenteDaoPostgres getInstance() {
        if (instance == null) {
            instance = new ConsulenteDaoPostgres();
            conn = DBManager.getInstance().getConnection();
        }
        return instance;
    }

    @Override
    public Consulente findByPIva(String pIva) {
        String query = "SELECT * FROM consulenti WHERE piva = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, pIva);
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                Consulente consulente = new Consulente();
                consulente.setPIva(rs.getString("piva"));
                consulente.setRagioneSociale(rs.getString("ragionesociale"));
                consulente.setEmail(rs.getString("email"));
                consulente.setTelefono(rs.getString("telefono"));
                consulente.setIndirizzo(rs.getString("indirizzo"));
                consulente.setImg(rs.getString("immagine"));
                return consulente;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean insert(Consulente consulente) {
        String query = "INSERT INTO consulenti (piva, ragionesociale, email, telefono, indirizzo, immagine) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, consulente.getPIva());
            st.setString(2, consulente.getRagioneSociale());
            st.setString(3, consulente.getEmail());
            st.setString(4, consulente.getTelefono());
            st.setString(5, consulente.getIndirizzo());
            st.setString(6, consulente.getImg());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Consulente consulente) {
        String query = "UPDATE consulenti SET ragionesociale = ?, email = ?, telefono = ?, indirizzo = ?, immagine = ? WHERE piva = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, consulente.getRagioneSociale());
            st.setString(2, consulente.getEmail());
            st.setString(3, consulente.getTelefono());
            st.setString(4, consulente.getIndirizzo());
            st.setString(5, consulente.getImg());
            st.setString(6, consulente.getPIva());
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
