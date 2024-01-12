package com.exam.esameweb24_backend.persistence;

import com.exam.esameweb24_backend.persistence.dao.*;
import com.exam.esameweb24_backend.persistence.dao.postgres.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {
    private static DBManager instance = null;
    private Connection conn = null;

    private DBManager() {}

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() {
        if (conn == null) {
            try {
                Properties properties = loadProperties();
                conn = DriverManager.getConnection(
                        properties.getProperty("db.url"),
                        properties.getProperty("db.user"),
                        properties.getProperty("db.password")
                );
                System.out.println("Connected to database");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return conn;
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find application.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading application.yml", e);
        }
        return properties;
    }

    public AziendaDao getAziendaDao() {return AziendaDaoPostgres.getInstance();}

    public ConsulenteDao getConsulenteDao() {return ConsulenteDaoPostgres.getInstance();}

    public CorsoDao getCorsoDao() {return CorsoDaoPostgres.getInstance();}

    public DocumentoDao getDocumentoDao() {return DocumentoDaoPostgres.getInstance();}

    public DipendenteDao getDipendenteDao() {return DipendenteDaoPostgres.getInstance();}

    public UserDao getUserDao() {return UserDaoPostgres.getInstance();}
}
