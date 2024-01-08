package com.exam.esameweb24_backend.persistence.model;

import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.persistence.DBManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class UserA extends User{

    // Dipendente Service

    @Override
    public ResponseEntity<List<Dipendente>> getDipendentiByPIva(String pIva) {
        if(this.pIva.equals(pIva))
            return new ResponseEntity<>(DBManager.getInstance().getDipendenteDao().findByAgency(pIva), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<Dipendente> getDipendente(Long id) {
        Dipendente dipendente = DBManager.getInstance().getDipendenteDao().findById(id);
        if (dipendente==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (this.pIva.equals(dipendente.getAzienda().getPIva()))
            return new ResponseEntity<>(dipendente, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> aggiungiDipendete(MultipartFile json, MultipartFile file) {

        // controllo se è stata aggiunta un'immagine
        boolean thereIsFile = file.getOriginalFilename().isEmpty();

        // se il json è vuoto allora non può usare il servizio
        if (json.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // converto il json in un dipendente
        Dipendente dipendente = Utility.jsonToDipendente(json);

        // fornisco l'azienda al dipendente per poterlo inserire nel database
        Azienda a = new Azienda();  // creo un'azienda vuota
        a.setPIva(this.pIva);  // gli assegno la partita iva dell'utente che ha effettuato la richiesta
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
                filePath = Utility.uploadFile(this.pIva, file);
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

    @Override
    public ResponseEntity<String> modificaDipendente(MultipartFile json, MultipartFile file) {

        // controllo se è stata aggiunta un'immagine
        boolean thereIsFile = !file.isEmpty();

        // se il json è vuoto allora non può usare il servizio
        if (json.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // converto il json in un dipendente
        Dipendente dipendente = Utility.jsonToDipendente(json);

        // controllo che il dipendente esista
        Dipendente d = DBManager.getInstance().getDipendenteDao().findById(dipendente.getId());
        if (d==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //controllo che l'azienda sia associata al dipendente da modificare
        if (this.pIva.equals(d.getAzienda().getPIva())) {

            // controllo se è stata caricata un'immagine
            if (thereIsFile) {
                String filePath;
                try {
                    //salvo il file nella cartella dei files
                    filePath = Utility.uploadFile(this.pIva, file);
                    // elimino il vecchio file se esiste
                    if(d.getImg()!=null) Utility.deleteFile(d.getImg());
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
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // da fare
    @Override
    public ResponseEntity<String> rimuoviDipendente(Long id) {
        // controllo se il dipendente esiste
        Dipendente d = DBManager.getInstance().getDipendenteDao().findById(id);
        if (d == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        //controllo che l'azienda sia associata al dipendente da rimuovere
        if (this.pIva.equals(d.getAzienda().getPIva()))
            // elimino il dipendente dal database
            if(DBManager.getInstance().getDipendenteDao().delete(id))
                return new ResponseEntity<> (HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
