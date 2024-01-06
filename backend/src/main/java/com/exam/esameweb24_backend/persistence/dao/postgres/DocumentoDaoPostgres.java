package com.exam.esameweb24_backend.persistence.dao.postgres;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.dao.DocumentoDao;
import com.exam.esameweb24_backend.persistence.model.Azienda;
import com.exam.esameweb24_backend.persistence.model.Documento;
import com.exam.esameweb24_backend.persistence.model.Dipendente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentoDaoPostgres implements DocumentoDao {

    private static Connection conn = null;

    private static DocumentoDaoPostgres instance = null;

    public static DocumentoDaoPostgres getInstance() {
        if (instance == null) {
            instance = new DocumentoDaoPostgres();
            conn = DBManager.getInstance().getConnection();
        }
        return instance;
    }

    @Override
    public List<Documento> findByEmployee(Long employeeID) {
        Dipendente dipendente = DBManager.getInstance().getDipendenteDao().findById(employeeID);
        List<Documento> documenti = new ArrayList<>();
        String query = "SELECT * FROM documenti WHERE dipendente  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, employeeID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Documento documento = new Documento();
                documento.setId(rs.getLong("id"));
                documento.setNome(rs.getString("nome"));
                documento.setUrl(rs.getString("url"));
                documento.setDataRilascio(rs.getDate("data_rilascio"));
                documento.setDataScadenza(rs.getDate("data_scadenza"));
                documento.setEmployee(dipendente);
                documento.setStato(rs.getString("stato"));
                documento.setFormato(rs.getString("formato"));
                documenti.add(documento);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return documenti;
    }

    @Override
    public List<Documento> findByAgency(String agencyPIva) {
        Azienda azienda = DBManager.getInstance().getAziendaDao().findByPIva(agencyPIva);
        List<Documento> documenti = new ArrayList<>();
        String query = "SELECT * FROM documenti WHERE agenzia  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, agencyPIva);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Documento documento = new Documento();
                documento.setId(rs.getLong("id"));
                documento.setNome(rs.getString("nome"));
                documento.setUrl(rs.getString("url"));
                documento.setDataRilascio(rs.getDate("data_rilascio"));
                documento.setDataScadenza(rs.getDate("data_scadenza"));
                documento.setAgency(azienda);
                documento.setStato(rs.getString("stato"));
                documento.setFormato(rs.getString("formato"));
                documenti.add(documento);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return documenti;
    }

    @Override
    public Documento findById(Long id) {
        String query = "SELECT * FROM documenti WHERE id  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Documento documento = new Documento();
                documento.setId(rs.getLong("id"));
                documento.setNome(rs.getString("nome"));
                documento.setUrl(rs.getString("url"));
                documento.setDataRilascio(rs.getDate("data_rilascio"));
                documento.setDataScadenza(rs.getDate("data_scadenza"));
                if(rs.getInt("dipendente") != 0)
                    documento.setEmployee(DBManager.getInstance().getDipendenteDao().findById(rs.getLong("dipendente")));
                if(rs.getString("agenzia") != null)
                    documento.setAgency(DBManager.getInstance().getAziendaDao().findByPIva(rs.getString("agenzia")));
                documento.setStato(rs.getString("stato"));
                documento.setFormato(rs.getString("formato"));
                return documento;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean insert(Documento documento) {
        String query = "INSERT INTO documenti (nome, url, data_rilascio, data_scadenza, dipendente, agenzia, stato, formato) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, documento.getNome());
            st.setString(2, documento.getUrl());
            st.setDate(3,  new java.sql.Date(documento.getDataRilascio().getTime()));
            st.setDate(4, new java.sql.Date(documento.getDataScadenza().getTime()));
            if(documento.getEmployee() != null) {
                st.setLong(5, documento.getEmployee().getId());
                st.setNull(6, java.sql.Types.VARCHAR);
            } else {
                st.setNull(5, java.sql.Types.INTEGER);
                if (documento.getAgency() != null)
                    st.setString(6, documento.getAgency().getPIva());
                else
                    return false;
            }
            st.setString(7, documento.getStato());
            st.setString(8, documento.getFormato());
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
