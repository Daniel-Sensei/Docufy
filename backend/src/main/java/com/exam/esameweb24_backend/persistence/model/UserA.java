package com.exam.esameweb24_backend.persistence.model;

import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.persistence.DBManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserA extends User
{


    // Azienda Service

    @Override
    public ResponseEntity<List<Azienda>> getAziende() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<Azienda> getAzienda(String pIva) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<Azienda> getProfile() {
        Azienda a = DBManager.getInstance().getAziendaDao().findByPIva(pIva);
        a.setConsulente(null);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> aggiungiAzienda(MultipartFile json, MultipartFile file) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> modificaAzienda(MultipartFile json, MultipartFile file) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> rimuoviAzienda(String pIva) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> modificaImmagineAzienda(String pIva, MultipartFile file) {

        // se il file è vuoto allora non può usare il servizio
        if (file.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // controllo che l'azienda abbia chiesto di modificare la propria immagine
        if(this.pIva.equals(pIva)) {
            Azienda a = DBManager.getInstance().getAziendaDao().findByPIva(pIva);
            String filePath;
            try {
                //salvo il file nella cartella dei files
                filePath = Utility.uploadFile(pIva, file);
                // elimino il vecchio file se esiste
                if(a.getImg()!=null) Utility.deleteFile(a.getImg());
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            // salvo il path del nuovo file nell'azienda
            a.setImg(filePath);

            // modifico l'azienda nel database
            if (DBManager.getInstance().getAziendaDao().update(a))
                return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> rimuoviImmagineAzienda(String pIva) {

        // controllo che il consulente sia associato all'azienda da modificare
        if(this.pIva.equals(pIva)){
            Azienda a = DBManager.getInstance().getAziendaDao().findByPIva(pIva);

            // elimino il vecchio file
            if(a.getImg()!=null) Utility.deleteFile(a.getImg());
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            // salvo il path null nell'azienda per indicare che non ha un'immagine
            a.setImg(null);

            // modifico l'azienda nel database
            if (DBManager.getInstance().getAziendaDao().update(a))
                return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }




    // Corso Service

    @Override
    public ResponseEntity<List<Corso>> getCorsiByAzienda(String pIva) {
        if(this.pIva.equals(pIva))
            return new ResponseEntity<>(DBManager.getInstance().getCorsoDao().findByAgency(pIva), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<List<Corso>> getCorsiByDipendente(Long id) {
        Dipendente dipendente = DBManager.getInstance().getDipendenteDao().findById(id);
        if (dipendente==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (this.pIva.equals(dipendente.getAzienda().getPIva()))
            return new ResponseEntity<>(DBManager.getInstance().getCorsoDao().findByEmployee(id), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<Corso> getCorso(Long id) {
        Corso corso = DBManager.getInstance().getCorsoDao().findById(id);
        if (corso==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(corso, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> aggiungiDipendentiCorso(Long idCorso, List<Long> dipendenti) {

        Corso c = DBManager.getInstance().getCorsoDao().findById(idCorso);
        if (c==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        for (Long id : dipendenti) {
            Dipendente d = DBManager.getInstance().getDipendenteDao().findById(id);
            if (d==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            if (!this.pIva.equals(d.getAzienda().getPIva())) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            DBManager.getInstance().getCorsoDao().addDipendente(idCorso, id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> aggiungiCorso(Corso corso, String pIva) {return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);}

    @Override
    public ResponseEntity<String> modificaCorso(Corso corso, String pIva) {return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);}

    @Override
    public ResponseEntity<String> rimuoviCorso(Long id) {return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);}




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
    public ResponseEntity<List<Dipendente>> getDipendentiByCorso(Long idCorso) {
        Corso corso = DBManager.getInstance().getCorsoDao().findById(idCorso);
        if (corso==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<Dipendente> d = DBManager.getInstance().getDipendenteDao().findByCorsoFrequentato(idCorso);
        List<Dipendente> dipendenti = new ArrayList<>();
        for (Dipendente dipendente : d) {
            if (this.pIva.equals(dipendente.getAzienda().getPIva()))
                dipendenti.add(dipendente);
        }
        return new ResponseEntity<>(dipendenti, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> aggiungiDipendete(MultipartFile json, MultipartFile file) {

        // controllo se è stata aggiunta un'immagine
        boolean thereIsFile = !(file.getOriginalFilename().isEmpty() || file.getOriginalFilename().equals("blob"));

        // se il json è vuoto allora non può usare il servizio
        if (json.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // converto il json in un dipendente
        Dipendente dipendente = Utility.jsonToObject(json, Dipendente.class);

        // fornisco l'azienda al dipendente per poterlo inserire nel database
        Azienda a = new Azienda();  // creo un'azienda vuota
        a.setPIva(this.pIva);  // gli assegno la partita iva dell'utente che ha effettuato la richiesta
        dipendente.setAzienda(a);   // associo l'azienda al dipendente

        // inserisco il dipendente nel database
        Long id = DBManager.getInstance().getDipendenteDao().insert(dipendente);
        if (id==null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        else dipendente.setId(id);

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
            if (!DBManager.getInstance().getDipendenteDao().update(dipendente)) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        // se tutto è andato a buon fine, restituisco l'OK
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> modificaDipendente(MultipartFile json, MultipartFile file) {

        // controllo se è stata aggiunta un'immagine
        boolean thereIsFile = !(file.getOriginalFilename().isEmpty());

        // se il json è vuoto allora non può usare il servizio
        if (json.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // converto il json in un dipendente
        Dipendente dipendente = Utility.jsonToObject(json, Dipendente.class);

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
            else dipendente.setImg(d.getImg());

            // modifico il dipendente nel database
            if (DBManager.getInstance().getDipendenteDao().update(dipendente))
                return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> rimuoviDipendente(Long id) {
        // controllo se il dipendente esiste
        Dipendente d = DBManager.getInstance().getDipendenteDao().findById(id);
        if (d == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        //controllo che l'azienda sia associata al dipendente da rimuovere
        if (this.pIva.equals(d.getAzienda().getPIva())) {
            // elimino il dipendente dal database
            rimuoviImmagineDipendente(id);
            if (DBManager.getInstance().getDipendenteDao().delete(id)) return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> rimuoviImmagineDipendente(Long id) {
        // controllo se il dipendente esiste
        Dipendente d = DBManager.getInstance().getDipendenteDao().findById(id);
        if (d == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        //controllo che l'azienda sia associata al dipendente da rimuovere
        if (this.pIva.equals(d.getAzienda().getPIva())) {
            // elimino il file dal server
            if (Utility.deleteFile(d.getImg())) {
                // elimino il path del file dal dipendente
                d.setImg("");
                // aggiorno il dipendente nel database
                if (DBManager.getInstance().getDipendenteDao().update(d))
                    return new ResponseEntity<>(HttpStatus.OK);
                else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }




    // Documento Service

    @Override
    public ResponseEntity<List<Documento>> getDocumentiAzienda(String pIva) {
        if(DBManager.getInstance().getAziendaDao().findByPIva(pIva)==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(this.pIva.equals(pIva))
            return new ResponseEntity<>(DBManager.getInstance().getDocumentoDao().findByAzienda(pIva), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<List<Documento>> getDocumentiDipendente(Long id) {
        Dipendente dipendente = DBManager.getInstance().getDipendenteDao().findById(id);
        if (dipendente==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (this.pIva.equals(dipendente.getAzienda().getPIva()))
            return new ResponseEntity<>(DBManager.getInstance().getDocumentoDao().findByDipendente(id), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<Documento> getDocumento(Long id) {
        Documento documento = DBManager.getInstance().getDocumentoDao().findById(id);
        if (documento==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // controllo che l'azienda richiedente sia associata al documento (se dell'azienda)
        // oppure che sia associata al dipendente che possiede il documento (se del dipendente)
        if ((documento.getDipendente()==null && this.pIva.equals(documento.getAzienda().getPIva()))||
                (documento.getDipendente()!=null && this.pIva.equals(documento.getDipendente().getAzienda().getPIva())))
            return new ResponseEntity<>(documento, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> aggiungiDocumento(MultipartFile json, MultipartFile file, String pIva, String cf) {

        // Controllo se è stato aggiunto il file e se è stato aggiunto un json del documento
        if (json.isEmpty() || file.getOriginalFilename().isBlank() || file.getOriginalFilename().equals("blob") || file.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Documento documento = Utility.jsonToObject(json, Documento.class);

        String filePath;
        try {
            if (cf != null) {
                // Per Dipendente
                Dipendente d = DBManager.getInstance().getDipendenteDao().findByCF(cf);
                if (d == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                if(!Utility.checkAgencyEmployeeCF(pIva, cf)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                filePath = Utility.uploadFile(cf, file);
            } else {
                // Per Azienda
                if (!this.pIva.equals(pIva)) {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
                filePath = Utility.uploadFile(pIva, file);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        documento.setFile(filePath);

        Long id = DBManager.getInstance().getDocumentoDao().insert(documento);
        if (id == null) {
            Utility.deleteFile(filePath);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> modificaDocumento(MultipartFile json, MultipartFile file, String pIva, String cf) {

        // controllo se è stato aggiunto il file e se è stato aggiunto un json del documento
        if (json.isEmpty() || file.getOriginalFilename().isBlank() || file.getOriginalFilename().equals("blob") || file.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // converto il json in un documento
        Documento documento = Utility.jsonToObject(json, Documento.class);

        // controllo che il documento esista
        Documento d = DBManager.getInstance().getDocumentoDao().findById(documento.getId());
        if (d == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // controllo che l'azienda sia associata al documento da modificare
        if (this.pIva.equals(pIva) || (cf!=null && Utility.checkAgencyEmployeeCF(this.pIva, cf))) {

            // salvo il file nella cartella dei files
            String filePath;
            try {
                filePath = Utility.uploadFile(cf == null ? pIva : cf , file);
                // elimino il vecchio file se esiste
                if(d.getFile()!=null) Utility.deleteFile(d.getFile());
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // salvo il path del nuovo file nel documento
            documento.setFile(filePath);

            // modifico il documento nel database
            if (DBManager.getInstance().getDocumentoDao().update(documento))
                return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> rimuoviDocumento(Long id) {
        // controllo se il documento esiste
        Documento d = DBManager.getInstance().getDocumentoDao().findById(id);
        if (d == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        //controllo che l'azienda sia associata al documento da rimuovere
        if ((d.getAzienda()!=null && this.pIva.equals(d.getAzienda().getPIva())) || (d.getDipendente()!=null && Utility.checkAgencyEmployeeCF(this.pIva, d.getDipendente().getCF()))) {
            // elimino il file dal server
            if(Utility.deleteFile(d.getFile()))
                // elimino il documento dal database
                if (DBManager.getInstance().getDocumentoDao().delete(id)) return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
