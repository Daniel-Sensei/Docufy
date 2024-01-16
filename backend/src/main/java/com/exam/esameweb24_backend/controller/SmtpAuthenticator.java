package com.exam.esameweb24_backend.controller;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class SmtpAuthenticator extends Authenticator {
    public SmtpAuthenticator() {

        super();
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        String username = "testforwebapplication@gmail.com";
        String password = "gfqopfdisafxscpz";
        if ((username != null) && (username.length() > 0) && (password != null)
                && (password.length   () > 0)) {

            return new PasswordAuthentication(username, password.toCharArray());
        }

        return null;
    }
}
