package com.exam.esameweb24_backend.controller;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:4200")
public class Auth {

    @PostMapping("/login")
    public AuthToken login(@RequestBody User user){
        User storedUser = DBManager.getInstance().getUserDao().findByEmail(user.getEmail());
        if(storedUser == null){
            return null;
        }
        if(!checkPassword(user, storedUser)){
            return null;
        }
        if(DBManager.getInstance().getUserDao().isConsultant(storedUser)) {
            return generateToken(user, "C");
        }
        else {
            return generateToken(user, "A");
        }
    }

    private boolean checkPassword(User user, User storedUser){
        return BCrypt.checkpw(user.getPassword(), storedUser.getPassword());
    }

    private AuthToken generateToken(User user, String role){
        String token = Utility.encodeBase64(user.getEmail() + ":" + user.getPassword() + "-" + role + ":" + user.getPIva());
        AuthToken auth = new AuthToken();
        auth.setToken(token);
        auth.setRole(role);
        return auth;
    }
}

