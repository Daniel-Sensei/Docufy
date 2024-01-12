package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.User;

public interface UserDao {
    public User findByEmail(String email);
    public User findByToken(String token);
    public boolean isConsultant(User user);
    public boolean insert(User user);
    public boolean updatePassword(User user);
    public boolean updateEmail(User user);
    public boolean delete(User user);
}
