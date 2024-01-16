package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.persistence.model.Ricerca;
import com.exam.esameweb24_backend.persistence.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:4200/")
public class RicercaService {

    @GetMapping("/search")
    public ResponseEntity<Ricerca> ricerca(HttpServletRequest req, @RequestParam String pIva, @RequestParam String q){

        User user = Utility.getRequestUser(req);

        if(user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.ricerca(pIva, q);
    }
}
