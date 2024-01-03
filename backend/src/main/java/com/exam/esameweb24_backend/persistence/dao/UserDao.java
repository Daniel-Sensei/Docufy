package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.User;

public interface UserDao {
    public User findByEmail(String passcode);
    public User findByToken(String token);
    public Boolean insert(User user);
    public Boolean update(User user);
}
