package com.exam.esameweb24_backend.persistence.dao.postgres;

import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.dao.UserDao;
import com.exam.esameweb24_backend.persistence.model.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoPostgres implements UserDao {

    private static Connection conn = null;

    private static UserDaoPostgres instance = null;

    public static UserDaoPostgres getInstance() {
        if (instance == null) {
            instance = new UserDaoPostgres();
            conn = DBManager.getInstance().getConnection();
        }
        return instance;
    }

    @Override
    public User findByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                User user = new User();
                user.setPIva(rs.getString("piva"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User findByToken(String token) {
        String email = Utility.getTokenEmail(token);
        String query = "SELECT * FROM users WHERE email = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                User user = new User();
                user.setPIva(rs.getString("piva"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User findByPIva(String pIva) {
        String query = "SELECT * FROM users WHERE piva = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, pIva);
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                User user = new User();
                user.setPIva(rs.getString("piva"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean isConsultant(User user) {
        return DBManager.getInstance().getConsulenteDao().findByPIva(user.getPIva()) != null;
    }

    @Override
    public boolean insert(User user) {
        String query = "INSERT INTO users (piva, email, password) VALUES (?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, user.getPIva());
            st.setString(2, user.getEmail());
            st.setString(3, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(User user) {
        String query = "UPDATE users SET email = ?, password = ? WHERE piva = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, user.getEmail());
            st.setString(2, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
            st.setString(3, user.getPIva());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(User user) {
        String query = "DELETE FROM users WHERE piva = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, user.getPIva());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
