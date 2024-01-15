package com.exam.esameweb24_backend.persistence.dao.postgres;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.dao.AziendaDao;
import com.exam.esameweb24_backend.persistence.model.Azienda;

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
                azienda.setConsulente(DBManager.getInstance().getConsulenteDao().findByPIva(rs.getString("consulente")));
                return azienda;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Azienda> findByCorsoAcquistato(Long idCorso) {
        List<Azienda> aziende = new ArrayList<>();
        String query = "SELECT * FROM aziende WHERE piva IN (SELECT azienda FROM corsi_aziende WHERE corso = ?)";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Azienda azienda = new Azienda();
                azienda.setPIva(rs.getString("piva"));
                azienda.setRagioneSociale(rs.getString("ragionesociale"));
                azienda.setEmail(rs.getString("email"));
                azienda.setTelefono(rs.getString("telefono"));
                azienda.setIndirizzo(rs.getString("indirizzo"));
                azienda.setImg(rs.getString("immagine"));
                azienda.setConsulente(DBManager.getInstance().getConsulenteDao().findByPIva(rs.getString("consulente")));
                aziende.add(azienda);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return aziende;
    }

    public ArrayList<Azienda> ricerca (List<String> q){
        ArrayList<Azienda> aziende = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM aziende WHERE ");
        for (int i = 0; i < q.size(); i++) {
            if (i == q.size() - 1)
                query.append("piva ILIKE ? OR ragionesociale ILIKE ? OR indirizzo ILIKE ? OR email ILIKE ? OR telefono ILIKE ?");
            else
                query.append("piva ILIKE ? OR ragionesociale ILIKE ? OR indirizzo ILIKE ? OR email ILIKE ? OR telefono ILIKE ? OR ");
        }
        try {
            PreparedStatement st = conn.prepareStatement(query.toString());
            for (int i = 1; i <= q.size(); i++)
                for (int j = 1; j <= 5; j++)
                    st.setString(i*j, "%"+q.get(i-1)+"%");
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Azienda azienda = new Azienda();
                azienda.setPIva(rs.getString("piva"));
                azienda.setRagioneSociale(rs.getString("ragionesociale"));
                azienda.setEmail(rs.getString("email"));
                azienda.setTelefono(rs.getString("telefono"));
                azienda.setIndirizzo(rs.getString("indirizzo"));
                azienda.setImg(rs.getString("immagine"));
                aziende.add(azienda);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return aziende;
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
            st.setString(7, azienda.getConsulente().getPIva());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Azienda azienda) {
        String query = "UPDATE aziende SET ragionesociale = ?, email = ?, telefono = ?, indirizzo = ?, immagine = ? WHERE piva = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, azienda.getRagioneSociale());
            st.setString(2, azienda.getEmail());
            st.setString(3, azienda.getTelefono());
            st.setString(4, azienda.getIndirizzo());
            st.setString(5, azienda.getImg());
            st.setString(6, azienda.getPIva());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String pIva) {
        try {
            conn.setAutoCommit(false); // Disabilita il commit automatico

            // elimino i documenti associati all'azienda
            String deleteDocumenti = "DELETE FROM documenti WHERE azienda = ?";
            PreparedStatement st = conn.prepareStatement(deleteDocumenti);
            st.setString(1, pIva);
            st.executeUpdate();

            // elimino l'azienda
            String query = "DELETE FROM aziende WHERE piva = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, pIva);
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