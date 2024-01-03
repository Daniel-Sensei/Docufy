package com.exam.esameweb24_backend.persistence.dao.postgres;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.dao.CourseDao;
import com.exam.esameweb24_backend.persistence.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoPostgres implements CourseDao {

    private static Connection conn = null;

    private static CourseDaoPostgres instance = null;

    public static CourseDaoPostgres getInstance() {
        if (instance == null) {
            instance = new CourseDaoPostgres();
            conn = DBManager.getInstance().getConnection();
        }
        return instance;
    }

    @Override
    public List<Course> findByConsultant(String consultantPIva) {
        Consultant consultant = DBManager.getInstance().getConsultantDao().findByPIva(consultantPIva);
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM corsi WHERE consulente  = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, consultant.getpIva());
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Course course = new Course();
                course.setId(rs.getLong("id"));
                course.setName(rs.getString("nome"));
                course.setPrice(rs.getDouble("prezzo"));
                course.setDescription(rs.getString("descrizione"));
                course.setDuration(rs.getInt("durata"));
                course.setConsultant(consultant);
                course.setCategory(rs.getString("categoria"));
                course.setSeats(rs.getInt("posti"));
                course.setAvailableSeats(rs.getInt("postidisponibili"));
                course.setFinalExam(rs.getBoolean("esamefinale"));
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    @Override
    public List<CourseBought> findByAgency(String agencyPIva) {
        Agency agency = DBManager.getInstance().getAgencyDao().findByPIva(agencyPIva);
        List<CourseBought> courses = new ArrayList<>();
        String query = "SELECT * FROM corsi_aziende WHERE azienda = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, agency.getpIva());
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                CourseBought course = new CourseBought();
                course.setCourse(DBManager.getInstance().getCourseDao().findById(rs.getLong("corso")));
                course.setAgency(agency);
                course.setDate(rs.getDate("data_acquisto"));
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    @Override
    public List<CourseAttended> findByEmployee(Long employeeID) {
        Employee employee = DBManager.getInstance().getEmployeeDao().findById(employeeID);
        List<CourseAttended> courses = new ArrayList<>();
        String query = "SELECT * FROM corsi_dipendenti WHERE dipendente = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, employeeID);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                CourseAttended course = new CourseAttended();
                course.setCourse(DBManager.getInstance().getCourseDao().findById(rs.getLong("corso")));
                course.setEmployee(employee);
                course.setStartingDate(rs.getDate("data_inizio"));
                course.setEndingDate(rs.getDate("data_fine"));
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    @Override
    public Course findById(Long id) {
        String query = "SELECT * FROM corsi WHERE id = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Course course = new Course();
                course.setId(rs.getLong("id"));
                course.setName(rs.getString("nome"));
                course.setPrice(rs.getDouble("prezzo"));
                course.setDescription(rs.getString("descrizione"));
                course.setDuration(rs.getInt("durata"));
                course.setConsultant(DBManager.getInstance().getConsultantDao().findByPIva(rs.getString("consulente")));
                course.setCategory(rs.getString("categoria"));
                course.setSeats(rs.getInt("posti"));
                course.setAvailableSeats(rs.getInt("postidisponibili"));
                course.setFinalExam(rs.getBoolean("esamefinale"));
                return course;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean insert(Course course) {
        String query = "INSERT INTO corsi (nome, prezzo, descrizione, durata, consulente, categoria, posti, postidisponibili, esamefinale) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, course.getName());
            st.setDouble(2, course.getPrice());
            st.setString(3, course.getDescription());
            st.setInt(4, course.getDuration());
            st.setString(5, course.getConsultant().getpIva());
            st.setString(6, course.getCategory());
            st.setInt(7, course.getSeats());
            st.setInt(8, course.getAvailableSeats());
            st.setBoolean(9, course.isFinalExam());
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
