package com.exam.esameweb24_backend.persistence.model;

import com.exam.esameweb24_backend.controller.PasswordHelper;
import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.controller.EmailSender;
import com.exam.esameweb24_backend.persistence.DBManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserC extends User
{

    public UserC() {
        super();
    }

    public UserC(String email, String password, String pIva) {
        super(email, password, pIva);
    }

    public UserC(User user) {
        super(user.email, user.password, user.pIva);
    }

    // Azienda Service

    @Override
    public ResponseEntity<List<Azienda>> getAziende() {
        List <Azienda> aziende = DBManager.getInstance().getAziendaDao().findByConsultant(pIva);
        return new ResponseEntity<>(aziende, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Azienda> getAzienda(String pIva) {
        Azienda azienda = DBManager.getInstance().getAziendaDao().findByPIva(pIva);
        if (azienda==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (Utility.checkConsultantAgency(this.pIva, azienda.getPIva()))
            return new ResponseEntity<>(azienda, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<Azienda> getProfile() {
        Consulente c = DBManager.getInstance().getConsulenteDao().findByPIva(this.pIva);
        Azienda a = new Azienda();
        a.setPIva(c.getPIva());
        a.setEmail(c.getEmail());
        a.setTelefono(c.getTelefono());
        a.setRagioneSociale(c.getRagioneSociale());
        a.setIndirizzo(c.getIndirizzo());
        a.setImg(c.getImg());
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> aggiungiAzienda(MultipartFile json, MultipartFile file) {

        // controllo se è stata aggiunta un'immagine
        boolean thereIsFile = !(file.getOriginalFilename().equals("blob") || file.isEmpty());

        // se il json è vuoto allora non può usare il servizio
        if (json.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // converto il json in un'azienda
        Azienda azienda = Utility.jsonToObject(json, Azienda.class);

        String tempPassword = PasswordHelper.generatePassword(8);

        // creo un nuovo utente
        User newUser = new User();
        newUser.setEmail(azienda.getEmail());
        newUser.setPassword(tempPassword);
        newUser.setPIva(azienda.getPIva());

        // inserisco il nuovo utente nel database
        if (DBManager.getInstance().getUserDao().insert(newUser)) {

            // fornisco la pIva del consulente
            Consulente c = new Consulente();
            c.setPIva(this.pIva);
            azienda.setConsulente(c);

            // inserisco l'azienda nel database
            if (DBManager.getInstance().getAziendaDao().insert(azienda)) {
                if (thereIsFile) {
                    String filePath;
                    try {
                        //salvo il file nella cartella dei files
                        filePath = Utility.uploadFile(azienda.getPIva(), file);
                    } catch (IOException e) {
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                    azienda.setImg(filePath);
                    // aggiorno il dipendente nel database
                    if (!DBManager.getInstance().getAziendaDao().update(azienda))
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                String body = azienda.getEmail()+":"+tempPassword;
                EmailSender.sendRegistrationMail(azienda.getEmail(), null, "Credenziali di accesso piattaforma Docufy", body);

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> modificaProfilo(MultipartFile json, MultipartFile file) {

        // controllo se è stata aggiunta un'immagine
        boolean thereIsFile = !(file.getOriginalFilename().equals("blob") || file.getOriginalFilename().isBlank() || file.isEmpty());

        // controllo se il json è vuoto
        if (json.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // converto il json in un'azienda
        Consulente consulente = Utility.jsonToObject(json, Consulente.class);

        if(!this.pIva.equals(consulente.getPIva())) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Consulente c = DBManager.getInstance().getConsulenteDao().findByPIva(this.pIva);

        // controllo se l'email è stata modificata
        if(!this.email.equals(consulente.getEmail())) {
            this.email = consulente.getEmail();
            if(DBManager.getInstance().getUserDao().updateEmail(this))
                return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (thereIsFile) {
            String filePath;
            try {
                //salvo il file nella cartella dei files
                filePath = Utility.uploadFile(consulente.getPIva(), file);
                // elimino il vecchio file se esiste
                if(c.getImg()!=null) Utility.deleteFile(c.getImg());
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            // salvo il path del nuovo file nella nuova azienda
            consulente.setImg(filePath);
        }
        else consulente.setImg(c.getImg());

        // modifico l'azienda nel database
        if (DBManager.getInstance().getConsulenteDao().update(consulente))
            return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> rimuoviAzienda(String pIva) {

        Azienda a = DBManager.getInstance().getAziendaDao().findByPIva(pIva);

        // controllo che l'azienda esista
        if (a==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // controllo che il consulente sia associato all'azienda da eliminare
        if(this.pIva.equals(a.getConsulente().getPIva())){
            // elimino il file dell'immagine del dipendente
            if(a.getImg()!=null)
                rimuoviImmagineAzienda(pIva);
            // elimino l'utente dal database e a cascata anche l'azienda
            if (DBManager.getInstance().getUserDao().delete(pIva)) return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> modificaImmagineAzienda(String pIva, MultipartFile file) {

        // se il file è vuoto allora non può usare il servizio
        if (file.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // controllo se l'azienda esiste
        Azienda a = DBManager.getInstance().getAziendaDao().findByPIva(pIva);
        if (a==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // controllo che il consulente sia associato all'azienda da modificare
        if(this.pIva.equals(a.getConsulente().getPIva())){
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

            // controllo se l'azienda esiste
            Azienda a = DBManager.getInstance().getAziendaDao().findByPIva(pIva);
            if (a==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            // controllo che il consulente sia associato all'azienda da modificare
            if(this.pIva.equals(a.getConsulente().getPIva())){
                // elimino il vecchio file se esiste
                if(a.getImg()!=null) Utility.deleteFile(a.getImg());
                else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                // salvo il path null nell'azienda da modificare
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
        if(Utility.checkConsultantAgency(this.pIva, pIva))
            return new ResponseEntity<>(DBManager.getInstance().getCorsoDao().findByAzienda(pIva), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<List<Corso>> getCorsiByDipendente(Long id) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<Corso> getCorso(Long id) {
        Corso corso = DBManager.getInstance().getCorsoDao().findById(id);
        if (corso==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (this.pIva.equals(corso.getConsulente().getPIva()))
            return new ResponseEntity<>(corso, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> aggiungiDipendentiCorso(Long idCorso, List<Dipendente> dipendenti) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> aggiungiCorso(Corso corso, String pIva) {

        if(corso==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Azienda a = DBManager.getInstance().getAziendaDao().findByPIva(pIva);

        if (a==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Consulente c = DBManager.getInstance().getConsulenteDao().findByPIva(this.pIva);
        corso.setConsulente(c);
        corso.setAzienda(a);
        corso.setPostiDisponibili(corso.getPosti());

        if(Utility.checkConsultantAgency(this.pIva, pIva)){
            if (DBManager.getInstance().getCorsoDao().insert(corso)!=null)
                    return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> modificaCorso(Corso corso, String pIva) {

        // controllo che il corso fornito non sia null
        if(corso==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Corso c = DBManager.getInstance().getCorsoDao().findById(corso.getId());
        // controllo che il corso esista
        if (c==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Azienda a = DBManager.getInstance().getAziendaDao().findByPIva(pIva);
        // controllo che l'azienda esista
        if (a==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // controllo che il consulente sia associato al corso da modificare
        if (!this.pIva.equals(c.getConsulente().getPIva()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        // controllo che l'azienda sia associata al corso da modificare
        if(!pIva.equals(c.getAzienda().getPIva()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if(!Utility.checkConsultantAgency(this.pIva, pIva))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if (DBManager.getInstance().getCorsoDao().update(corso))
            return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> rimuoviCorso(Long id) {

        // controllo che il corso esista
        Corso c = DBManager.getInstance().getCorsoDao().findById(id);
        if (c==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // controllo che il consulente sia associato al corso da eliminare
        if (this.pIva.equals(c.getConsulente().getPIva())) {
            if (DBManager.getInstance().getCorsoDao().delete(id)) return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }




    // Dipendente Service

    @Override
    public List<Dipendente> getDipendenti() {
        List <Azienda> aziende = DBManager.getInstance().getAziendaDao().findByConsultant(pIva);
        List <Dipendente> dipendenti = new ArrayList<>();
        for (Azienda a : aziende)
            dipendenti.addAll(DBManager.getInstance().getDipendenteDao().findByAzienda(a.getPIva()));
        return dipendenti;
    }

    @Override
    public ResponseEntity<List<Dipendente>> getDipendentiByPIva(String pIva) {
        if(Utility.checkConsultantAgency(this.pIva, pIva))
            return new ResponseEntity<>(DBManager.getInstance().getDipendenteDao().findByAzienda(pIva), HttpStatus.OK);
        if (this.pIva.equals(pIva))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<Dipendente> getDipendente(Long id) {
        Dipendente dipendente = DBManager.getInstance().getDipendenteDao().findById(id);
        if (dipendente==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (Utility.checkConsultantAgency(this.pIva, dipendente.getAzienda().getPIva()))
            return new ResponseEntity<>(dipendente, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<List<Dipendente>> getDipendentiByCorso(Long id) {
        Corso c = DBManager.getInstance().getCorsoDao().findById(id);
        if (c==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (this.pIva.equals(c.getConsulente().getPIva()))
            return new ResponseEntity<>(DBManager.getInstance().getDipendenteDao().findByCorsoFrequentato(id), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<List<Dipendente>> getDipendentiNonIscritti(Long idCorso){
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> aggiungiDipendentiDaExcel(MultipartFile file){
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> aggiungiDipendentiDaCsv(MultipartFile file){
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> aggiungiDipendete(MultipartFile json, MultipartFile file) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> modificaDipendente(MultipartFile json, MultipartFile file) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> rimuoviDipendente(Long id) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> rimuoviImmagineDipendente(Long id) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }




    // Documento Service

    @Override
    public ResponseEntity<List<Documento>> getDocumentiAzienda(String pIva) {
        if(DBManager.getInstance().getAziendaDao().findByPIva(pIva)==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(Utility.checkConsultantAgency(this.pIva, pIva))
            return new ResponseEntity<>(DBManager.getInstance().getDocumentoDao().findByAzienda(pIva), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<List<Documento>> getDocumentiDipendente(Long id) {
        Dipendente dipendente = DBManager.getInstance().getDipendenteDao().findById(id);
        if (dipendente==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (Utility.checkConsultantAgency(this.pIva, dipendente.getAzienda().getPIva()))
            return new ResponseEntity<>(DBManager.getInstance().getDocumentoDao().findByDipendente(id), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<Documento> getDocumento(Long id) {
        Documento documento = DBManager.getInstance().getDocumentoDao().findById(id);
        if (documento==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // controllo che l'azienda richiedente sia associata al documento (se dell'azienda)
        // oppure che sia associata al dipendente che possiede il documento (se del dipendente)
        if ((documento.getDipendente()==null && Utility.checkConsultantAgency(this.pIva, documento.getAzienda().getPIva()))||
                (documento.getDipendente()!=null && Utility.checkConsultantAgency(this.pIva, documento.getDipendente().getAzienda().getPIva())))
            return new ResponseEntity<>(documento, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<List<Documento>> getDocumentiValidi(String pIva) {
        if(DBManager.getInstance().getAziendaDao().findByPIva(pIva)==null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(!Utility.checkConsultantAgency(this.pIva, pIva))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return getDocumentiByStato(pIva, "Valido");
    }

    @Override
    public ResponseEntity<List<Documento>> getDocumentiInScadenza(String pIva) {
        if(DBManager.getInstance().getAziendaDao().findByPIva(pIva)==null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(!Utility.checkConsultantAgency(this.pIva, pIva))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return getDocumentiByStato(pIva, "In Scadenza");
    }

    @Override
    public ResponseEntity<List<Documento>> getDocumentiScaduti(String pIva) {
        if(DBManager.getInstance().getAziendaDao().findByPIva(pIva)==null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(!Utility.checkConsultantAgency(this.pIva, pIva))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return getDocumentiByStato(pIva, "Scaduto");
    }

    @Override
    public ResponseEntity<String> aggiungiDocumento (MultipartFile json, MultipartFile file, String pIva, String cf){

        // controllo se è stato aggiunto il file e se è stato aggiunto un json del documento
        if (json.isEmpty() || file.getOriginalFilename().isBlank() || file.getOriginalFilename().equals("blob") || file.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // converto il json in un documento
        Documento documento = Utility.jsonToObject(json, Documento.class);

        if(cf!=null){
            Dipendente d = DBManager.getInstance().getDipendenteDao().findByCF(cf);
            if (d == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            if(!Utility.checkConsultantAgency(this.pIva, DBManager.getInstance().getDipendenteDao().findByCF(cf).getAzienda().getPIva()))
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            documento.setDipendente(d);
        } else {
            Azienda a = DBManager.getInstance().getAziendaDao().findByPIva(pIva);
            if (a == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            if(!Utility.checkConsultantAgency(this.pIva, pIva))
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            documento.setAzienda(a);
        }

        documento.setStato(checkDataDocumento(documento));

        String filePath;
        try {
            filePath = Utility.uploadFile(cf==null ? pIva : cf , file);
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

        documento.setStato(checkDataDocumento(documento));

        // controllo che il documento esista
        Documento d = DBManager.getInstance().getDocumentoDao().findById(documento.getId());
        if (d == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(cf!=null) {
            Dipendente dipendente = DBManager.getInstance().getDipendenteDao().findByCF(cf);
            if (dipendente == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            if(!Utility.checkConsultantAgency(this.pIva, DBManager.getInstance().getDipendenteDao().findByCF(cf).getAzienda().getPIva()))
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            else documento.setDipendente(dipendente);
        } else {
            Azienda azienda = DBManager.getInstance().getAziendaDao().findByPIva(pIva);
            if (azienda == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            if(!Utility.checkConsultantAgency(this.pIva, pIva))
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            else documento.setAzienda(azienda);
        }

        // salvo il file nella cartella dei files
        String filePath;
        try {
            //imposto il nome del file come pIva se è un'azienda, cf se è un dipendente
            filePath = Utility.uploadFile(cf == null ? pIva : cf, file);
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

    @Override
    public ResponseEntity<String> rimuoviDocumento(Long id) {

            // controllo che il documento esista
            Documento d = DBManager.getInstance().getDocumentoDao().findById(id);
            if (d == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            // controllo che il consulente sia associato all'azienda/dipendente proprietario del documento da eliminare
            if ((d.getDipendente()==null && Utility.checkConsultantAgency(this.pIva, d.getAzienda().getPIva())) || (d.getDipendente()!=null && Utility.checkConsultantAgency(this.pIva, d.getDipendente().getAzienda().getPIva()))) {

                // elimino il file del documento
                if(d.getFile()!=null) Utility.deleteFile(d.getFile());
                else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

                // elimino il documento dal database
                if (DBManager.getInstance().getDocumentoDao().delete(id)) return new ResponseEntity<>(HttpStatus.OK);
                else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    // Ricerca Service

    @Override
    public ResponseEntity<Ricerca> ricerca(String q) {
        List<String> params = new ArrayList<>(Arrays.asList(q.split("%22")));
        for(String s : params) s.toLowerCase();
        Ricerca r = new Ricerca();
        r.setAziende(DBManager.getInstance().getAziendaDao().ricerca(params));
        ArrayList<Dipendente> dip = new ArrayList<>();
        for(Azienda a : DBManager.getInstance().getAziendaDao().findByConsultant(pIva))
            dip.addAll(DBManager.getInstance().getDipendenteDao().findByAzienda(a.getPIva()));
        r.setDipendenti(dip);
        List<Documento> documenti = DBManager.getInstance().getDocumentoDao().ricerca(params);
        for(Documento d : documenti){
            if(d.getAzienda()!=null && d.getAzienda().getConsulente().getPIva().equals(pIva)) r.addToDocumenti(d);
            else if(d.getDipendente()!=null && d.getDipendente().getAzienda().getConsulente().getPIva().equals(pIva)) r.addToDocumenti(d);
        }
        return new ResponseEntity<>(r, HttpStatus.OK);
        }
}
