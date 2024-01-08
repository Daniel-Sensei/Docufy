package com.exam.esameweb24_backend.controller.service;

import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.*;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class DipendenteService {

    // Questo servizio fornisce i soli dipendenti associati alla partita iva passata come parametro
    // Il suo utilizzo è riservato al consulente associato a quella P.Iva
    @GetMapping("/dipendenti")
    public ResponseEntity<List<Dipendente>> getDipendentiByPIva(HttpServletRequest req, @RequestParam String pIva){

        User user = Utility.getRequestUser(req);
        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (user==null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return user.getDipendentiByPIva(pIva);
    }

    // Questo servizio restituisce il dipendente con l'id passato come parametro
    // Il suo utilizzo è riservato all'azienda per cui lavora il dipendente
    // e al consulente associato a quella P.Iva
    @GetMapping ("/dipendente")
    public ResponseEntity<Dipendente> getDipendente(HttpServletRequest req, @RequestParam Long id){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);
        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (user==null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // prendo il dipendente con l'id passato come parametro (controllando che esista)
        Dipendente dipendente = DBManager.getInstance().getDipendenteDao().findById(id);
        if (dipendente==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // prendo l'azienda associata al dipendente (non è possibile che sia null)
        Azienda azienda = DBManager.getInstance().getAziendaDao().findByPIva(dipendente.getAzienda().getPIva());
        // se è un'azienda ed è associata al dipendente OPPURE se è un consulente ed è associato all'azienda del dipendente
        if ((!Utility.isConsultant(token) && user.getPIva().equals(azienda.getPIva())) || (Utility.isConsultant(token) && Utility.checkConsultantAgency(user.getPIva(), azienda.getPIva())))
            return new ResponseEntity<>(dipendente, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // Questo servizio permette di aggiungere un dipendente
    // Il suo utilizzo è riservato all'azienda per cui lavora il dipendente
    @PostMapping("/aggiungi-dipendente")
    public ResponseEntity<String> aggiuntaDipendete(HttpServletRequest req, @RequestParam("dipendente") MultipartFile json,  @RequestParam("file") MultipartFile file){

        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);

        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (user==null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        boolean thereIsFile = !file.isEmpty();

        // se il json è vuoto allora non può usare il servizio
        if (json.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //controllo che l'utente sia un'azienda
        if (!Utility.isConsultant(token)) {

            // converto il json in un oggetto Dipendente
            Dipendente dipendente = Utility.jsonToDipendente(json);

            // fornisco l'azienda al dipendente per poterlo inserire nel database
            Azienda a = new Azienda();  // creo un'azienda vuota
            a.setPIva(user.getPIva());  // gli assegno la partita iva dell'utente che ha effettuato la richiesta
            dipendente.setAzienda(a);   // associo l'azienda al dipendente
            // inserisco il dipendente nel database
            Long id = DBManager.getInstance().getDipendenteDao().insert(dipendente);
            if (id==null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            else
                dipendente.setId(id);
            // salvo l'immagine solo se è stata caricata
            if (thereIsFile) {
                String filePath;
                try {
                    //salvo il file nella cartella dei files
                    filePath = Utility.uploadFile(file, user);
                } catch (IOException e) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                // salvo il path del file nel dipendente
                dipendente.setImg(filePath);
                // aggiorno il dipendente nel database
                if (!DBManager.getInstance().getDipendenteDao().update(dipendente) ) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            // se tutto è andato a buon fine, restituisco l'OK
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // Questo servizio permette di modificare un dipendente
    // Il suo utilizzo è riservato all'azienda per cui lavora il dipendente
    @PostMapping("/modifica-dipendente")
    public ResponseEntity<String> modificaDipendente(HttpServletRequest req, @RequestParam("dipendente") MultipartFile json, @RequestParam("file") MultipartFile file){
        String token = Utility.getToken(req);
        User user = Utility.getRequestUser(req);
        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (user==null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        //controllo che l'utente sia un'azienda
        if (!Utility.isConsultant(token)) {
            // converto il json in un oggetto Dipendente
            Dipendente dipendente = Utility.jsonToDipendente(json);
            // controllo che il dipendente esista
            Dipendente d = DBManager.getInstance().getDipendenteDao().findById(dipendente.getId());
            if (d == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            //controllo che l'azienda sia associata al dipendente da modificare
            if (user.getPIva().equals(d.getAzienda().getPIva())) {
                // controllo se è stata caricata un'immagine
                if (!file.isEmpty()) {
                    String filePath;
                    try {
                        //salvo il file nella cartella dei files
                        filePath = Utility.uploadFile(file, user);
                        FileService.deleteFile(req, d.getImg());
                    } catch (IOException e) {
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                    // salvo il path del nuovo file nel dipendente
                    dipendente.setImg(filePath);
                }
                // modifico il dipendente nel database
                if (DBManager.getInstance().getDipendenteDao().update(dipendente))
                    return new ResponseEntity<>(HttpStatus.OK);
                else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // Questo servizio permette di eliminare un dipendente
    // Il suo utilizzo è riservato all'azienda per cui lavora il dipendente
    @GetMapping("/rimuovi-dipendente")
    public ResponseEntity<String> eliminazioneDipendente(HttpServletRequest req, @RequestParam Long id){
        User user = Utility.getRequestUser(req);
        // se l'utente è null (non è loggato) allora non può usare il servizio
        if (user==null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // controllo se il dipendente esiste
        Dipendente d = DBManager.getInstance().getDipendenteDao().findById(id);
        if (d == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        //controllo che l'azienda sia associata al dipendente da rimuovere
        if (user.getPIva().equals(d.getAzienda().getPIva()))
            // elimino il dipendente dal database
            if(DBManager.getInstance().getDipendenteDao().delete(id))
                return new ResponseEntity<> (HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
