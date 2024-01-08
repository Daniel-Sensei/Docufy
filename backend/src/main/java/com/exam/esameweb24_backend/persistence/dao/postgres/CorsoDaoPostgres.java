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
        Consulente consulente = DBManager.getInstance().getConsulenteDao().findByPIva(consultantPIva);
        List<Corso> courses = new ArrayList<>();
        String query = "SELECT * FROM corsi WHERE consulente  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, consulente.getPIva());
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Corso corso = new Corso();
                corso.setId(rs.getLong("id"));
                corso.setNome(rs.getString("nome"));
                corso.setPrezzo(rs.getDouble("prezzo"));
                corso.setDescrizione(rs.getString("descrizione"));
                corso.setDurata(rs.getInt("durata"));
                corso.setConsultant(consulente);
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
    public List<CorsoAcquistato> findByAgency(String agencyPIva) {
        Azienda azienda = DBManager.getInstance().getAziendaDao().findByPIva(agencyPIva);
        List<CorsoAcquistato> courses = new ArrayList<>();
        String query = "SELECT * FROM corsi_aziende WHERE azienda = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, azienda.getPIva());
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                CorsoAcquistato course = new CorsoAcquistato();
                course.setCourse(DBManager.getInstance().getCorsoDao().findById(rs.getLong("corso")));
                course.setAgency(azienda);
                course.setDate(rs.getDate("data_acquisto"));
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    @Override
    public List<CorsoFrequentato> findByEmployee(Long employeeID) {
        Dipendente dipendente = DBManager.getInstance().getDipendenteDao().findById(employeeID);
        List<CorsoFrequentato> corsi = new ArrayList<>();
        String query = "SELECT * FROM corsi_dipendenti WHERE dipendente = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, employeeID);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                CorsoFrequentato course = new CorsoFrequentato();
                course.setCourse(DBManager.getInstance().getCorsoDao().findById(rs.getLong("corso")));
                course.setEmployee(dipendente);
                course.setStartingDate(rs.getDate("data_inizio"));
                course.setEndingDate(rs.getDate("data_fine"));
                corsi.add(course);
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
                corso.setConsultant(DBManager.getInstance().getConsulenteDao().findByPIva(rs.getString("consulente")));
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
    public boolean insert(Corso corso) {
        String query = "INSERT INTO corsi (nome, prezzo, descrizione, durata, consulente, categoria, posti, postidisponibili, esamefinale) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, corso.getNome());
            st.setDouble(2, corso.getPrezzo());
            st.setString(3, corso.getDescrizione());
            st.setInt(4, corso.getDurata());
            st.setString(5, corso.getConsultant().getPIva());
            st.setString(6, corso.getCategoria());
            st.setInt(7, corso.getPosti());
            st.setInt(8, corso.getPostiDisponibili());
            st.setBoolean(9, corso.isFinalExam());
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
}