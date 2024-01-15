package com.exam.esameweb24_backend.controller.servlet;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.Dipendente;
import com.exam.esameweb24_backend.persistence.model.User;
import com.exam.esameweb24_backend.persistence.model.UserA;
import com.exam.esameweb24_backend.persistence.model.UserC;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/all-dipendenti")
public class DipendentiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session != null) {
            // get user from session
            User user = (User) session.getAttribute("user");

            if(user == null) {
                RequestDispatcher dispatcher = req.getRequestDispatcher("/not-authorized.html");
                dispatcher.forward(req, resp);
            }
            else{
                if(DBManager.getInstance().getUserDao().isConsultant(user)){
                    user = new UserC(user);
                }
                else{
                    user = new UserA(user);
                }

                List<Dipendente> dipendenti = user.getDipendenti();
                req.setAttribute("dipendenti", dipendenti);

                // Reindirizza a dipendenti.html;
                RequestDispatcher dispatcher = req.getRequestDispatcher("/views/dipendenti.html");
                dispatcher.forward(req, resp);
            }
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/not-authorized.html");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("elimina".equals(action)) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                // get user from session
                User user = (User) session.getAttribute("user");

                if(user == null) {
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/not-authorized.html");
                    dispatcher.forward(req, resp);
                }
                else {
                    if (DBManager.getInstance().getUserDao().isConsultant(user)) {
                        //user = new UserC(user);
                        resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Non sei autorizzato ad accedere a questa risorsa");
                        return;
                    } else {
                        user = new UserA(user);
                    }

                    // Ottieni l'ID del dipendente da eliminare dalla richiesta
                    String dipendenteIdParam = req.getParameter("dipendenteId");

                    // Verifica che l'ID sia presente e sia un numero valido
                    if (dipendenteIdParam != null && dipendenteIdParam.matches("\\d+")) {
                        long dipendenteId = Long.parseLong(dipendenteIdParam);
                        user.rimuoviDipendente(dipendenteId);

                        // Reindirizza a dipendenti.html
                        resp.sendRedirect(req.getContextPath() + "/all-dipendenti");
                    } else {
                        // Gestisci l'errore quando l'ID del dipendente non è valido
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID del dipendente non valido");
                        return; // Termina il metodo qui per evitare di continuare con il forward
                    }
                }
            } else {
                RequestDispatcher dispatcher = req.getRequestDispatcher("/not-authorized.html");
                dispatcher.forward(req, resp);
            }
        }
    }

}
