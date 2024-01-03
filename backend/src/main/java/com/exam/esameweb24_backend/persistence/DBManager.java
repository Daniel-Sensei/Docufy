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
                // System.out.println("Connected to database"); OK!
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
            throw new RuntimeException("Error loading application.properties", e);
        }
        return properties;
    }

    public AgencyDao getAgencyDao() {return AgencyDaoPostgres.getInstance();}

    public ConsultantDao getConsultantDao() {return ConsultantDaoPostgres.getInstance();}

    public CourseDao getCourseDao() {return CourseDaoPostgres.getInstance();}

    public DocumentDao getDocumentDao() {return DocumentDaoPostgres.getInstance();}

    public EmployeeDao getEmployeeDao() {return EmployeeDaoPostgres.getInstance();}
}
