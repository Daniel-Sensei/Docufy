package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.Azienda;
import com.exam.esameweb24_backend.persistence.model.Dipendente;
import com.exam.esameweb24_backend.persistence.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class DipendenteService {

    // Questo servizio fornisce i soli dipendenti associati alla partita iva passata come parametro
    // Il suo utilizzo è riservato al consulente associato a quella P.Iva
    @GetMapping("/dipendenti")
    public List<Dipendente> getDipendentiByPIva(HttpServletRequest req, @RequestParam String pIva){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);
        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (user==null) return null;
        if(Utility.isConsultant(token))   // se è un consulente
            if (Utility.checkConsultantAgency(user.getPIva(), pIva)) // se l'azienda è associata al consulente
                return DBManager.getInstance().getDipendenteDao().findByAgency(user.getPIva());
        return null;
    }

    // Questo servizio restituisce tutti i dipendenti e funziona controllando il ruolo dell'utente che ha effettuato la richiesta
    // Se è un consulente, allora può vedere tutti i dipendenti associati alle agenzie che gestisce
    // Se è un'agenzia, allora può vedere solo i propri dipendenti
    // Se non è nessuno dei due, allora non può vedere nessun dipendente
    @GetMapping("/my-dipendenti")
    public List<Dipendente> getDipendenti(HttpServletRequest req){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);
        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (user==null) return null;
        if(Utility.isConsultant(token))  // se è un consulente
            return DBManager.getInstance().getDipendenteDao().findByConsultant(user.getPIva());
        else // se è un'azienda
            return DBManager.getInstance().getDipendenteDao().findByAgency(user.getPIva());
    }

    // Questo servizio restituisce il dipendente con l'id passato come parametro
    // Il suo utilizzo è riservato all'azienda per cui lavora il dipendente
    // e al consulente associato a quella P.Iva
    @GetMapping ("/dipendente")
    public Dipendente getDipendente(HttpServletRequest req, @RequestParam Long id){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);
        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (user==null) return null;
        // prendo il dipendente con l'id passato come parametro (controllando che esista)
        Dipendente dipendente = DBManager.getInstance().getDipendenteDao().findById(id);
        if (dipendente==null) return null;
        // prendo l'azienda associata al dipendente (non è possibile che sia null)
        Azienda azienda = DBManager.getInstance().getAziendaDao().findByPIva(dipendente.getAzienda().getPIva());
        // se è un'azienda ed è associata al dipendente    OPPURE    se è un consulente ed è associato all'azienda del dipendente
        if ((!Utility.isConsultant(token) && user.getPIva().equals(azienda.getPIva())) || (Utility.isConsultant(token) && Utility.checkConsultantAgency(user.getPIva(), azienda.getPIva())))
            return dipendente;
        return null;
    }

    // Questo servizio permette di aggiungere un dipendente
    // Il suo utilizzo è riservato all'azienda per cui lavora il dipendente
    @PostMapping("/aggiungi-dipendente")
    public String aggiuntaDipendete(HttpServletRequest req, @RequestBody Dipendente dipendente){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);
        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (user==null) return "false: utente non loggato";
        //controllo che l'utente sia un'azienda
        if (!Utility.isConsultant(token)) {
            // fornisco l'azienda al dipendente per poterlo inserire nel database
            Azienda a = new Azienda();  // creo un'azienda vuota
            a.setPIva(user.getPIva());  // gli assegno la partita iva dell'utente che ha effettuato la richiesta
            dipendente.setAzienda(a);   // associo l'azienda al dipendente
            // inserisco il dipendente nel database
            return DBManager.getInstance().getDipendenteDao().insert(dipendente) ? "true" : "false: errore durante l'inserimento";
        }
        return "false: utente non autorizzato";
    }

    // Questo servizio permette di modificare un dipendente
    // Il suo utilizzo è riservato all'azienda per cui lavora il dipendente
    @PostMapping("/modifica-dipendente")
    public String modificaDipendente(HttpServletRequest req, @RequestBody Dipendente dipendente){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);
        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (user==null) return "false: utente non loggato";
        //controllo che l'utente sia un'azienda
        if (!Utility.isConsultant(token)) {
            // controllo che il dipendente esista
            Dipendente d = DBManager.getInstance().getDipendenteDao().findById(dipendente.getId());
            if (d == null) return "false: dipendente non trovato";
            //controllo che l'azienda sia associata al dipendente da modificare
            if (user.getPIva().equals(d.getAzienda().getPIva()))
                // modifico il dipendente nel database
                return DBManager.getInstance().getDipendenteDao().update(dipendente) ? "true" : "false: errore durante la modifica";
        }
        return "false: utente non autorizzato";
    }

    // Questo servizio permette di eliminare un dipendente
    // Il suo utilizzo è riservato all'azienda per cui lavora il dipendente
    @GetMapping("/rimuovi-dipendente")
    public String eliminazioneDipendente(HttpServletRequest req, @RequestParam Long id){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);
        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (user==null) return "false: utente non loggato";
        // controllo se il dipendente esiste
        Dipendente d = DBManager.getInstance().getDipendenteDao().findById(id);
        if (d == null) return "false: dipendente non trovato";
        //controllo che l'azienda sia associata al dipendente da rimuovere
        if (user.getPIva().equals(d.getAzienda().getPIva()))
            // elimino il dipendente dal database
            return DBManager.getInstance().getDipendenteDao().delete(id) ? "true" : "false: errore durante l'eliminazione";
        return "false: utente non autorizzato";
    }
}
