package com.exam.esameweb24_backend.controller;

import com.exam.esameweb24_backend.controller.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class EmailSendController {

    private EmailService emailService;

    public EmailSendController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String sendMail(String to, String[] cc, String subject, String body){
        return emailService.sendMail(to, cc, subject, body);
    }
}
