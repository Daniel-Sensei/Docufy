package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.controller.EmailSender;
import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.persistence.model.Email;
import com.exam.esameweb24_backend.persistence.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200/")
public class EmailService {

    @PostMapping("/send-mail")
    public ResponseEntity<String> sendConfirmationMail(HttpServletRequest req, @RequestBody Email mail){

        User user = Utility.getRequestUser(req);

        if(user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if(mail == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String body = "Abbiamo preso in carico la Vostra segnalazione, di seguito un riepilogo del messaggio inviato.\n\nOggetto: "+mail.getOggetto()+"\nMessaggio: "+mail.getMessaggio();

        if(EmailSender.sendConfirmationMail(mail.getMail(), null, "Conferma richiesta di contatto", body))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
