package com.exam.esameweb24_backend.persistence.dao.postgres;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.dao.DocumentoDao;
import com.exam.esameweb24_backend.persistence.model.Azienda;
import com.exam.esameweb24_backend.persistence.model.Dipendente;
import com.exam.esameweb24_backend.persistence.model.Documento;

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
    public List<Documento> getAll(){
        List<Documento> documenti = new ArrayList<>();
        String query = "SELECT * FROM documenti";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Documento documento = new Documento();
                documento.setId(rs.getLong("id"));
                documento.setNome(rs.getString("nome"));
                documento.setFile(rs.getString("url"));
                documento.setDataRilascio(rs.getDate("rilascio"));
                documento.setDataScadenza(rs.getDate("scadenza"));
                if(rs.getInt("dipendente") != 0)
                    documento.setDipendente(DBManager.getInstance().getDipendenteDao().findById(rs.getLong("dipendente")));
                if(rs.getString("azienda") != null)
                    documento.setAzienda(DBManager.getInstance().getAziendaDao().findByPIva(rs.getString("azienda")));
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
    public List<Documento> findByDipendente(Long idDipendente) {

        Dipendente d = DBManager.getInstance().getDipendenteDao().findById(idDipendente);
        Dipendente dipendente = new Dipendente();
        dipendente.setId(d.getId());
        dipendente.setCF(d.getCF());

        List<Documento> documenti = new ArrayList<>();
        String query = "SELECT * FROM documenti WHERE dipendente  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, idDipendente);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Documento documento = new Documento();
                documento.setId(rs.getLong("id"));
                documento.setNome(rs.getString("nome"));
                documento.setFile(rs.getString("url"));
                documento.setDataRilascio(rs.getDate("rilascio"));
                documento.setDataScadenza(rs.getDate("scadenza"));
                documento.setDipendente(dipendente);
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
    public List<Documento> findByAzienda(String pIva) {

        Azienda a = new Azienda();
        a.setPIva(pIva);

        List<Documento> documenti = new ArrayList<>();
        String query = "SELECT * FROM documenti WHERE azienda  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, pIva);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Documento documento = new Documento();
                documento.setId(rs.getLong("id"));
                documento.setNome(rs.getString("nome"));
                documento.setFile(rs.getString("url"));
                documento.setDataRilascio(rs.getDate("rilascio"));
                documento.setDataScadenza(rs.getDate("scadenza"));
                documento.setAzienda(a);
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
                documento.setFile(rs.getString("url"));
                documento.setDataRilascio(rs.getDate("rilascio"));
                documento.setDataScadenza(rs.getDate("scadenza"));
                if(rs.getInt("dipendente") != 0)
                    documento.setDipendente(DBManager.getInstance().getDipendenteDao().findById(rs.getLong("dipendente")));
                if(rs.getString("azienda") != null)
                    documento.setAzienda(DBManager.getInstance().getAziendaDao().findByPIva(rs.getString("azienda")));
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
    public ArrayList<Documento> ricerca(List<String> q) {
        ArrayList<Documento> documenti = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM documenti WHERE ");
        for (int i = 0; i < q.size(); i++) {
            query.append("nome ILIKE ? OR formato ILIKE ?");
            if (i < q.size() - 1) query.append(" OR ");
        }
        try {
            PreparedStatement st = conn.prepareStatement(query.toString());

            int index = 1;
            for (int i = 1; i <= q.size(); i++)
                for (int j = 1; j <= 2; j++)
                    st.setString(index++, "%"+q.get(i-1)+"%");

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Documento documento = new Documento();
                documento.setId(rs.getLong("id"));
                documento.setNome(rs.getString("nome"));
                documento.setFile(rs.getString("url"));
                documento.setDataRilascio(rs.getDate("rilascio"));
                documento.setDataScadenza(rs.getDate("scadenza"));
                if(rs.getInt("dipendente") != 0)
                    documento.setDipendente(DBManager.getInstance().getDipendenteDao().findById(rs.getLong("dipendente")));
                if(rs.getString("azienda") != null)
                    documento.setAzienda(DBManager.getInstance().getAziendaDao().findByPIva(rs.getString("azienda")));
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
    public Long insert(Documento documento) {
        String query = "INSERT INTO documenti (nome, url, rilascio, scadenza, ";
        String query2 = "stato, formato) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
        if(documento.getDipendente() != null) {
            query += "dipendente, ";
        } else {
            if (documento.getAzienda() != null) {
                query += "azienda, ";
            }
            else return null;
        }
        try {
            PreparedStatement st = conn.prepareStatement(query+query2);
            st.setString(1, documento.getNome());
            st.setString(2, documento.getFile());
            st.setDate(3,  new java.sql.Date(documento.getDataRilascio().getTime()));
            st.setDate(4, new java.sql.Date(documento.getDataScadenza().getTime()));
            if(documento.getDipendente() != null) {
                st.setLong(5, documento.getDipendente().getId());
            } else {
                st.setString(5, documento.getAzienda().getPIva());
            }
            st.setString(6, documento.getStato());
            st.setString(7, documento.getFormato());

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
    public boolean update(Documento documento) {
        String query = "UPDATE documenti SET nome = ?, url = ?, rilascio = ?, scadenza = ?, dipendente = ?, azienda = ?, stato = ?, formato = ? WHERE id = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, documento.getNome());
            st.setString(2, documento.getFile());
            st.setDate(3,  new java.sql.Date(documento.getDataRilascio().getTime()));
            st.setDate(4, new java.sql.Date(documento.getDataScadenza().getTime()));
            if(documento.getDipendente() != null) {
                st.setLong(5, documento.getDipendente().getId());
                st.setString(6, null);
            } else {
                if (documento.getAzienda() != null) {
                    st.setLong(5, 0);
                    st.setString(6, documento.getAzienda().getPIva());
                }
                else
                    return false;
            }
            st.setString(7, documento.getStato());
            st.setString(8, documento.getFormato());
            st.setLong(9, documento.getId());
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
