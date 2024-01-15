package com.exam.esameweb24_backend.persistence.dao.postgres;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.dao.DipendenteDao;
import com.exam.esameweb24_backend.persistence.model.Dipendente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DipendenteDaoPostgres implements DipendenteDao {

    private static Connection conn = null;

    private static DipendenteDaoPostgres instance = null;

    public static DipendenteDaoPostgres getInstance() {
        if (instance == null) {
            instance = new DipendenteDaoPostgres();
            conn = DBManager.getInstance().getConnection();
        }
        return instance;
    }

    @Override
    public List<Dipendente> findByAzienda(String agencyPIva) {
        List<Dipendente> dipendenti = new ArrayList<>();
        String query = "SELECT * FROM dipendenti WHERE azienda  = ? ORDER BY cognome ASC";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, agencyPIva);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Dipendente dipendente = new Dipendente();
                dipendente.setId(rs.getLong("id"));
                dipendente.setCF(rs.getString("cf"));
                dipendente.setNome(rs.getString("nome"));
                dipendente.setCognome(rs.getString("cognome"));
                dipendente.setDataNascita(rs.getDate("data_nascita"));
                dipendente.setEmail(rs.getString("email"));
                dipendente.setTelefono(rs.getString("telefono"));
                dipendente.setResidenza(rs.getString("indirizzo"));
                dipendente.setDataAssunzione(rs.getDate("data_assunzione"));
                dipendente.setRuolo(rs.getString("ruolo"));
                dipendente.setImg(rs.getString("foto"));
                dipendenti.add(dipendente);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dipendenti;
    }

    /**
     *  UNUSED METHOD TO BE REMOVED BEFORE DELIVERY

    @Override
    public List<Dipendente> findByConsultant(String consultantPIva) {
        List<Azienda> aziende = DBManager.getInstance().getAziendaDao().findByConsultant(consultantPIva);
        List<Dipendente> dipendenti = new ArrayList<>();
        for (Azienda azienda : aziende) {
            String query = "SELECT * FROM dipendenti WHERE azienda  = ?";
            try {
                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1, azienda.getPIva());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Dipendente dipendente = new Dipendente();
                    dipendente.setId(rs.getLong("id"));
                    dipendente.setCF(rs.getString("cf"));
                    dipendente.setNome(rs.getString("nome"));
                    dipendente.setCognome(rs.getString("cognome"));
                    dipendente.setDataNascita(rs.getDate("data_nascita"));
                    dipendente.setEmail(rs.getString("email"));
                    dipendente.setTelefono(rs.getString("telefono"));
                    dipendente.setResidenza(rs.getString("indirizzo"));
                    dipendente.setDataAssunzione(rs.getDate("data_assunzione"));
                    dipendente.setRuolo(rs.getString("ruolo"));
                    dipendente.setImg(rs.getString("foto"));
                    System.out.println(dipendente);
                    dipendenti.add(dipendente);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return dipendenti;
    }

     **/

    @Override
    public Dipendente findById(Long id) {
        String query = "SELECT * FROM dipendenti WHERE id  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Dipendente dipendente = new Dipendente();
                dipendente.setId(rs.getLong("id"));
                dipendente.setCF(rs.getString("cf"));
                dipendente.setNome(rs.getString("nome"));
                dipendente.setCognome(rs.getString("cognome"));
                dipendente.setDataNascita(rs.getDate("data_nascita"));
                dipendente.setEmail(rs.getString("email"));
                dipendente.setTelefono(rs.getString("telefono"));
                dipendente.setResidenza(rs.getString("indirizzo"));
                dipendente.setAzienda(DBManager.getInstance().getAziendaDao().findByPIva(rs.getString("azienda")));
                dipendente.setDataAssunzione(rs.getDate("data_assunzione"));
                dipendente.setRuolo(rs.getString("ruolo"));
                dipendente.setImg(rs.getString("foto"));
                return dipendente;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Dipendente findByCF(String cf) {
        String query = "SELECT * FROM dipendenti WHERE cf  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, cf);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Dipendente dipendente = new Dipendente();
                dipendente.setId(rs.getLong("id"));
                dipendente.setCF(rs.getString("cf"));
                dipendente.setNome(rs.getString("nome"));
                dipendente.setCognome(rs.getString("cognome"));
                dipendente.setDataNascita(rs.getDate("data_nascita"));
                dipendente.setEmail(rs.getString("email"));
                dipendente.setTelefono(rs.getString("telefono"));
                dipendente.setResidenza(rs.getString("indirizzo"));
                dipendente.setAzienda(DBManager.getInstance().getAziendaDao().findByPIva(rs.getString("azienda")));
                dipendente.setDataAssunzione(rs.getDate("data_assunzione"));
                dipendente.setRuolo(rs.getString("ruolo"));
                dipendente.setImg(rs.getString("foto"));
                return dipendente;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Dipendente> findByCorsoFrequentato(Long idCorso) {
        List<Dipendente> dipendenti = new ArrayList<>();
        String query = "SELECT * FROM dipendenti WHERE id IN (SELECT dipendente FROM corsi_dipendenti WHERE corso = ?)";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, idCorso);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Dipendente dipendente = new Dipendente();
                dipendente.setId(rs.getLong("id"));
                dipendente.setCF(rs.getString("cf"));
                dipendente.setNome(rs.getString("nome"));
                dipendente.setCognome(rs.getString("cognome"));
                dipendente.setDataNascita(rs.getDate("data_nascita"));
                dipendente.setEmail(rs.getString("email"));
                dipendente.setTelefono(rs.getString("telefono"));
                dipendente.setResidenza(rs.getString("indirizzo"));
                dipendente.setDataAssunzione(rs.getDate("data_assunzione"));
                dipendente.setRuolo(rs.getString("ruolo"));
                dipendente.setImg(rs.getString("foto"));
                dipendenti.add(dipendente);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dipendenti;
    }

    @Override
    public ArrayList<Dipendente> ricerca(String azienda, List<String> q){
        ArrayList<Dipendente> dipendenti = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM dipendenti WHERE azienda = ? AND (");
        for (int i = 0; i < q.size(); i++) {
            query.append("cf ILIKE ? OR nome ILIKE ? OR cognome ILIKE ? OR email ILIKE ? OR telefono ILIKE ? OR indirizzo ILIKE ? OR ruolo ILIKE ?");
            if (i < q.size() - 1) query.append(" OR ");
        }
        query.append(") ORDER BY cognome ASC");
        try {
            PreparedStatement st = conn.prepareStatement(query.toString());
            st.setString(1, azienda);

            int index = 2;
            for (int i = 1; i <= q.size(); i++)
                for (int j = 1; j <= 7; j++)
                    st.setString(index++, "%"+q.get(i-1)+"%");

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Dipendente dipendente = new Dipendente();
                dipendente.setId(rs.getLong("id"));
                dipendente.setCF(rs.getString("cf"));
                dipendente.setNome(rs.getString("nome"));
                dipendente.setCognome(rs.getString("cognome"));
                dipendente.setDataNascita(rs.getDate("data_nascita"));
                dipendente.setEmail(rs.getString("email"));
                dipendente.setTelefono(rs.getString("telefono"));
                dipendente.setResidenza(rs.getString("indirizzo"));
                dipendente.setDataAssunzione(rs.getDate("data_assunzione"));
                dipendente.setRuolo(rs.getString("ruolo"));
                dipendente.setImg(rs.getString("foto"));
                dipendenti.add(dipendente);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dipendenti;
    }

    // viene inserito il dipendente, dopodiché viene assegnato l'id del dipendente appena inserito
    @Override
    public Long insert(Dipendente dipendente) {
        String query = "INSERT INTO dipendenti (cf, nome, cognome, data_nascita, email, telefono, indirizzo, azienda, data_assunzione, ruolo, foto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, dipendente.getCF());
            st.setString(2, dipendente.getNome());
            st.setString(3, dipendente.getCognome());
            st.setDate(4, new java.sql.Date(dipendente.getDataNascita().getTime()));
            st.setString(5, dipendente.getEmail());
            st.setString(6, dipendente.getTelefono());
            st.setString(7, dipendente.getResidenza());
            st.setString(8, dipendente.getAzienda().getPIva());
            st.setDate(9, new java.sql.Date(dipendente.getDataAssunzione().getTime()));
            st.setString(10, dipendente.getRuolo());
            st.setString(11, dipendente.getImg());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Dipendente dipendente) {
        String query = "UPDATE dipendenti SET cf = ?, nome = ?, cognome = ?, data_nascita = ?, email = ?, telefono = ?, indirizzo = ?, data_assunzione = ?, ruolo = ?, foto = ? WHERE id = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, dipendente.getCF());
            st.setString(2, dipendente.getNome());
            st.setString(3, dipendente.getCognome());
            st.setDate(4, new java.sql.Date(dipendente.getDataNascita().getTime()));
            st.setString(5, dipendente.getEmail());
            st.setString(6, dipendente.getTelefono());
            st.setString(7, dipendente.getResidenza());
            st.setDate(8, new java.sql.Date(dipendente.getDataAssunzione().getTime()));
            st.setString(9, dipendente.getRuolo());
            st.setString(10, dipendente.getImg());
            st.setLong(11, dipendente.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            conn.setAutoCommit(false); // Disabilita il commit automatico

            // elimino i documenti associati in quanto gestiti senza foreign key
            String deleteDocumenti = "DELETE FROM documenti WHERE dipendente = ?";
            PreparedStatement st = conn.prepareStatement(deleteDocumenti);
            st.setLong(1, id);
            st.executeUpdate();

            // elimino il dipendente
            String query = "DELETE FROM dipendenti WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setLong(1, id);
            pst.executeUpdate();

            conn.commit(); // Esegue il commit delle modifiche

            return true;
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); // In caso di errore, esegue il rollback delle modifiche
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Rollback failed", ex);
            }
            throw new RuntimeException("Failed to delete employee", e);
        } finally {
            try {
                conn.setAutoCommit(true); // Riattiva il commit automatico
            } catch (SQLException ex) {
                throw new RuntimeException("Failed to set auto commit to true", ex);
            }
        }
    }
}
