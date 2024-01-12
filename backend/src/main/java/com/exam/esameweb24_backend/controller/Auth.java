package com.exam.esameweb24_backend.controller;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200")
public class Auth {

    @PostMapping("/login")
    public AuthToken login(@RequestBody User user){
        User storedUser = DBManager.getInstance().getUserDao().findByEmail(user.getEmail());
        if(storedUser == null){
            return null;
        }
        if(!Utility.checkPassword(user.getPassword(), storedUser)){
            return null;
        }
        user.setPIva(storedUser.getPIva());
        if(DBManager.getInstance().getUserDao().isConsultant(storedUser)) {
            return generateToken(user, "C");
        }
        else {
            return generateToken(user, "A");
        }
    }

    @PostMapping("/cambio-password")
    public ResponseEntity<String> cambioPassword(HttpServletRequest req, @RequestBody Password password){

        User user = Utility.getRequestUser(req);

        if(user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.cambioPassword(password.getOldPw(), password.getNewPw());
    }

    private AuthToken generateToken(User user, String role){
        String token = Utility.encodeBase64(user.getEmail() + ":tkn:" +  BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)) + ":tkn:" + role);
        AuthToken auth = new AuthToken();
        auth.setToken(token);
        auth.setRole(role);
        return auth;
    }
}

