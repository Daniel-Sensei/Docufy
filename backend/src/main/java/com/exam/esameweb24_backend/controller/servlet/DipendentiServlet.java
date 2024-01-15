package com.exam.esameweb24_backend.controller.servlet;

import com.exam.esameweb24_backend.persistence.DBManager;
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

@WebServlet("/all-dipendenti")
public class DipendentiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session != null) {
            System.out.println("Sessione valida");
            // get user from session
            User user = (User) session.getAttribute("user");

            if(user == null) {
                System.out.println("User null");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/not-authorized.html");
                dispatcher.forward(req, resp);
                return;
            }
            else{
                if(DBManager.getInstance().getUserDao().isConsultant(user)){
                    user = (UserC) user;
                }
                else{
                    user = (UserA) user;
                }

                //TODO: user.getDipendenti()
                // Se esegue userA --> restituisce i suoi dipendenti
                // Se esegue userC --> restituisce i dipendenti di tutte le sue sottoaziende
            }

            // Reindirizza a dipendenti.html;
            RequestDispatcher dispatcher = req.getRequestDispatcher("/views/dipendenti.html");
            dispatcher.forward(req, resp);
        } else {
            System.out.println("Sessione non valida");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/not-authorized.html");
            dispatcher.forward(req, resp);
        }
    }
}
