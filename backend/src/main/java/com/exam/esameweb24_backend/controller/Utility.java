package com.exam.esameweb24_backend.controller;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Base64;

public class Utility {

    public static String encodeBase64(String value){
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

    public static String decodeBase64(String value){
        return new String(Base64.getDecoder().decode(value.getBytes()));
    }

    public static String getToken(HttpServletRequest req){
        String auth = req.getHeader("Authorization");
        return auth.substring("Basic ".length());
    }

    public static String getTokenEmail(String token){
        return (decodeBase64(token).split("-")[0]).split(":")[0];
    }

    public static String getTokenPassword(String token){
        return (decodeBase64(token).split("-")[0]).split(":")[1];
    }

    public static String getTokenRole(String token){
        return (decodeBase64(token).split("-")[1]).split(":")[0];
    }

    public static String getTokenPIva(String token){
        return (decodeBase64(token).split("-")[1]).split(":")[1];
    }

    public static Boolean isConsultant(String token) {
        return getTokenRole(token).equals("C");
    }

    public static Boolean checkConsultantAgency(String consultant, String agency) {
        return (DBManager.getInstance().getAziendaDao().findByConsultant(consultant).stream().anyMatch(azienda -> azienda.getPIva().equals(agency)));
    }

    public static User getRequestUser(HttpServletRequest req){
        return DBManager.getInstance().getUserDao().findByToken(getToken(req));
    }

}
