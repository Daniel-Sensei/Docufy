package com.exam.esameweb24_backend.persistence.dao.postgres;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.dao.CorsoDao;
import com.exam.esameweb24_backend.persistence.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CorsoDaoPostgres implements CorsoDao {

    private static Connection conn = null;

    private static CorsoDaoPostgres instance = null;

    public static CorsoDaoPostgres getInstance() {
        if (instance == null) {
            instance = new CorsoDaoPostgres();
            conn = DBManager.getInstance().getConnection();
        }
        return instance;
    }

    @Override
    public List<Corso> findByConsultant(String consultantPIva) {
        List<Corso> courses = new ArrayList<>();
        String query = "SELECT * FROM corsi WHERE consulente  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, consultantPIva);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Corso corso = new Corso();
                corso.setId(rs.getLong("id"));
                corso.setNome(rs.getString("nome"));
                corso.setPrezzo(rs.getDouble("prezzo"));
                corso.setDescrizione(rs.getString("descrizione"));
                corso.setDurata(rs.getInt("durata"));
                corso.setCategoria(rs.getString("categoria"));
                corso.setPosti(rs.getInt("posti"));
                corso.setPostiDisponibili(rs.getInt("postidisponibili"));
                corso.setEsameFinale(rs.getBoolean("esamefinale"));
                courses.add(corso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    @Override
    public List<Corso> findByAgency(String pIva) {
        List<Corso> corsi = new ArrayList<>();
        String query = "SELECT * from corsi WHERE id IN (SELECT corso FROM corsi_aziende WHERE azienda = ?)";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, pIva);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Corso corso = new Corso();
                corso.setId(rs.getLong("id"));
                corso.setNome(rs.getString("nome"));
                corso.setPrezzo(rs.getDouble("prezzo"));
                corso.setDescrizione(rs.getString("descrizione"));
                corso.setDurata(rs.getInt("durata"));
                corso.setCategoria(rs.getString("categoria"));
                corso.setPosti(rs.getInt("posti"));
                corso.setPostiDisponibili(rs.getInt("postidisponibili"));
                corso.setEsameFinale(rs.getBoolean("esamefinale"));
                corsi.add(corso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return corsi;
    }

    @Override
    public List<Corso> findByEmployee(Long idDipendente) {
        List<Corso> corsi = new ArrayList<>();
        String query = "SELECT * FROM corsi WHERE id IN (SELECT corso FROM corsi_dipendenti WHERE dipendente = ?)";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, idDipendente);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Corso corso = new Corso();
                corso.setId(rs.getLong("id"));
                corso.setNome(rs.getString("nome"));
                corso.setPrezzo(rs.getDouble("prezzo"));
                corso.setDescrizione(rs.getString("descrizione"));
                corso.setDurata(rs.getInt("durata"));
                corso.setCategoria(rs.getString("categoria"));
                corso.setPosti(rs.getInt("posti"));
                corso.setPostiDisponibili(rs.getInt("postidisponibili"));
                corso.setEsameFinale(rs.getBoolean("esamefinale"));
                corsi.add(corso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return corsi;
    }

    @Override
    public Corso findById(Long id) {
        String query = "SELECT * FROM corsi WHERE id = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Corso corso = new Corso();
                corso.setId(rs.getLong("id"));
                corso.setNome(rs.getString("nome"));
                corso.setPrezzo(rs.getDouble("prezzo"));
                corso.setDescrizione(rs.getString("descrizione"));
                corso.setDurata(rs.getInt("durata"));
                corso.setConsulente(DBManager.getInstance().getConsulenteDao().findByPIva(rs.getString("consulente")));
                corso.setCategoria(rs.getString("categoria"));
                corso.setPosti(rs.getInt("posti"));
                corso.setPostiDisponibili(rs.getInt("postidisponibili"));
                corso.setEsameFinale(rs.getBoolean("esamefinale"));
                return corso;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Long insert(Corso corso) {
        String query = "INSERT INTO corsi (nome, prezzo, descrizione, durata, consulente, categoria, posti, postidisponibili, esamefinale) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, corso.getNome());
            st.setDouble(2, corso.getPrezzo());
            st.setString(3, corso.getDescrizione());
            st.setInt(4, corso.getDurata());
            st.setString(5, corso.getConsulente().getPIva());
            st.setString(6, corso.getCategoria());
            st.setInt(7, corso.getPosti());
            st.setInt(8, corso.getPostiDisponibili());
            st.setBoolean(9, corso.isFinalExam());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Corso corso) {
        String query = "UPDATE corsi SET nome = ?, prezzo = ?, descrizione = ?, durata = ?, categoria = ?, posti = ?, postidisponibili = ?, esamefinale = ? WHERE id = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, corso.getNome());
            st.setDouble(2, corso.getPrezzo());
            st.setString(3, corso.getDescrizione());
            st.setInt(4, corso.getDurata());
            st.setString(5, corso.getCategoria());
            st.setInt(6, corso.getPosti());
            st.setInt(7, corso.getPostiDisponibili());
            st.setBoolean(8, corso.isFinalExam());
            st.setLong(9, corso.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM corsi WHERE id = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addDipendente(Long idCorso, Long idDipendente) {
        String query = "INSERT INTO corsi_dipendenti (corso, dipendente, data_iscrizione) VALUES (?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, idCorso);
            st.setLong(2, idDipendente);
            st.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addAzienda(Long idCorso, String pIva) {
        String query = "INSERT INTO corsi_aziende (corso, azienda, data_acquisto) VALUES (?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, idCorso);
            st.setString(2, pIva);
            st.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
