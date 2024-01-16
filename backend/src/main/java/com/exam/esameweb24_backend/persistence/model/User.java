package com.exam.esameweb24_backend.persistence.model;

import com.exam.esameweb24_backend.controller.Utility;
import com.exam.esameweb24_backend.persistence.DBManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class User {

    protected String email;

    protected String password;

    protected String pIva;

    public User() {}

    public User(String email, String password, String pIva) {
        this.email = email;
        this.password = password;
        this.pIva = pIva;
    }

    /**
     * Returns the email of the user.
     *
     * @return a String containing the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email a String containing the email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the user.
     *
     * @return a String containing the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password a String containing the password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the Partita Iva of the user.
     *
     * @return a String containing the Partita Iva of the user
     */
    public String getPIva() {
        return pIva;
    }

    /**
     * Sets the pIva of the user.
     *
     * @param pIva a String containing the Partita Iva of the user
     */
    public void setPIva(String pIva) {
        this.pIva = pIva;
    }




    // Azienda Service

    /**
     * Returns the list of all the {@link Azienda aziende} in the database.
     *
     * @return the list of all the {@link Azienda aziende} in the database with an HttpStatus based on the outcome
     */
    public ResponseEntity<List<Azienda>> getAziende(){return null;}

    /**
     * Returns the {@link Azienda azienda} with the given Partita Iva.
     *
     * @param pIva a String containing the Partita Iva of the azienda
     *
     * @return the {@link Azienda azienda} with the given Partita Iva with an HttpStatus based on the outcome
     */
    public ResponseEntity<Azienda> getAzienda(String pIva){return null;}

    /**
     * Returns the {@link Azienda azienda} or {@link Consulente consulente} that is currently logged by checking the token given.
     *
     * @return the {@link Azienda azienda} or {@link Consulente consulente} (as an Azienda) that is currently logged with an HttpStatus based on the outcome
     */
    public ResponseEntity<Azienda> getProfile(){return null;}

    /**
     * Returns the outcome of the addition of the {@link Azienda azienda} in the database.
     * The {@link Azienda azienda} is added only if the Partita Iva is not already present in the database.
     * The {@link Azienda azienda} is added only if the email is not already present in the database.
     * The {@link Azienda azienda} is added only if the phone number is not already present in the database.
     *
     * @param json a MultipartFile containing the JSON of the {@link Azienda azienda} to add
     * @param file A MultipartFile containing the image of the {@link Azienda azienda} to add
     *             This parameter accepts null values.
     *
     * @return the outcome of the addition of the {@link Azienda azienda} in the database as an HttpStatus
     */
    public ResponseEntity<String> aggiungiAzienda(MultipartFile json, MultipartFile file){return null;}

    /**
     * Returns the outcome of the modification of the {@link Azienda azienda} or {@link Consulente consulente} data in the database.
     * The {@link Azienda azienda} or {@link Consulente consulente} is modified only if the Partita Iva is already present in the database.
     *
     * @param json a MultipartFile containing the JSON of the {@link Azienda azienda} or {@link Consulente consulente} to modify
     * @param file A MultipartFile containing the image of the {@link Azienda azienda} or {@link Consulente consulente} to modify
     *             This parameter accepts null values.
     *
     * @return  the outcome of the modification of the {@link Azienda azienda} in the database as an HttpStatus
     */
    public ResponseEntity<String> modificaProfilo(MultipartFile json, MultipartFile file){return null;}

    /**
     * Returns the outcome of the removal of the {@link Azienda azienda} in the database.
     * The {@link Azienda azienda} is removed only if the Partita Iva is already present in the database.
     *
     * @param pIva a String containing the Partita Iva of the {@link Azienda azienda} to remove
     *
     * @return the outcome of the removal of the {@link Azienda azienda} in the database as an HttpStatus
     */
    public ResponseEntity<String> rimuoviAzienda(String pIva){return null;}

    /**
     * Returns the outcome of the modification of the image of the {@link Azienda azienda} or {@link Consulente consulente} in the database.
     * The image is modified only if the Partita Iva is already present in the database.
     *
     * @param pIva a String containing the Partita Iva of the {@link Azienda azienda} or {@link Consulente consulente} whose image should be modified
     * @param file A MultipartFile containing the image of the {@link Azienda azienda} or {@link Consulente consulente} to change
     *
     * @return the outcome of the addition of the image of the {@link Azienda azienda} or {@link Consulente consulente} in the database as an HttpStatus
     */
    public ResponseEntity<String> modificaImmagineAzienda(String pIva, MultipartFile file){return null;}

    /**
     * Returns the outcome of the removal of the image of the {@link Azienda azienda} or {@link Consulente consulente} in the database.
     * The {@link Azienda azienda} is removed only if the Partita Iva is already present in the database.
     *
     * @param pIva A String containing the Partita Iva of the {@link Azienda azienda} or {@link Consulente consulente} from which the image should be removed.
     *
     * @return the outcome of the removal of the image of the {@link Azienda azienda} or {@link Consulente consulente} in the database as an HttpStatus
     */
    public ResponseEntity<String> rimuoviImmagineAzienda(String pIva){return null;}

    /**
     * Return the outcome of the password change of the {@link Azienda azienda} or {@link Consulente consulente} in the database.
     *
     * @param oldPw a String containing the old password of the {@link Azienda azienda} or {@link Consulente consulente}
     * @param newPw a String containing the new password of the {@link Azienda azienda} or {@link Consulente consulente}
     *
     * @return the outcome of the password change of the {@link Azienda azienda} or {@link Consulente consulente} in the database as an HttpStatus
     */
    public ResponseEntity<String> cambioPassword(String oldPw, String newPw) {
        if(!Utility.checkPassword(oldPw, this)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        this.password = newPw;
        if (DBManager.getInstance().getUserDao().updatePassword(this))
            return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }




    // Corso Service

    /**
     * Returns a list of {@link Corso corsi} in the database associated with the {@link Azienda azienda} with the given Partita Iva.
     *
     * @param pIva a String containing the Partita Iva of the {@link Azienda azienda} whose {@link Corso corsi} should be returned
     *
     * @return a list of {@link Corso corsi} in the database associated with the given Partita Iva with an HttpStatus based on the outcome
     */
    public ResponseEntity<List<Corso>> getCorsiByAzienda(String pIva){return null;}

    /**
     * Returns a list of {@link Corso corsi} in the database associated with the {@link Dipendente dipendente} with the given id.
     *
     * @param id a Long containing the id of the {@link Dipendente dipendente} whose {@link Corso corsi} should be returned
     *
     * @return a list of {@link Corso corsi} in the database associated with the given id with an HttpStatus based on the outcome
     */
    public ResponseEntity<List<Corso>> getCorsiByDipendente(Long id){return null;}

    /**
     * Returns the {@link Corso corso} with the given id.
     *
     * @param id a Long containing the id of the {@link Corso corso} to return
     *
     * @return the {@link Corso corso} with the given id with an HttpStatus based on the outcome
     */
    public ResponseEntity<Corso> getCorso(Long id){return null;}

    /**
     * Returns the outcome of the addition of the {@link Dipendente dipendenti} to the {@link Corso corso} with the given id.
     *
     * @param idCorso a Long containing the id of the {@link Corso corso} to which the {@link Dipendente dipendente} should be added
     * @param dipendenti a List of {@link Dipendente dipendenti} to add to the {@link Corso corso}
     *
     * @return the outcome of the addition of the {@link Dipendente dipendenti} to the {@link Corso corso} with the given id as an HttpStatus
     */
    public ResponseEntity<String> aggiungiDipendentiCorso(Long idCorso, List<Dipendente> dipendenti){return null;}

    /**
     * Returns the outcome of the addition of the {@link Corso corso} in the database.
     * The {@link Corso corso} is added only if the id is not already present in the database.
     *
     * @param corso a Corso containing the {@link Corso corso} to add
     * @param pIva a String containing the Partita Iva of the {@link Azienda azienda} to which the {@link Corso corso} should be added
     *
     * @return the outcome of the addition of the {@link Corso corso} in the database as an HttpStatus
     */
    public ResponseEntity<String> aggiungiCorso(Corso corso, String pIva){return null;}

    /**
     * Returns the outcome of the modification of the {@link Corso corso} in the database.
     * The {@link Corso corso} is modified only if the id is already present in the database.
     *
     * @param corso a Corso containing the {@link Corso corso} to modify
     *
     * @return the outcome of the modification of the {@link Corso corso} in the database as an HttpStatus
     */
    public ResponseEntity<String> modificaCorso(Corso corso){return null;}

    /**
     * Returns the outcome of the removal of the {@link Corso corso} in the database.
     * The {@link Corso corso} is removed only if the id is already present in the database.
     *
     * @param id a Long containing the id of the {@link Corso corso} to remove
     *
     * @return the outcome of the removal of the {@link Corso corso} in the database as an HttpStatus
     */
    public ResponseEntity<String> rimuoviCorso(Long id){return null;}




    // Dipendente Service

    /**
     * Returns a list of {@link Dipendente dipendenti} in the database associated with the currently logged {@link Azienda azienda}.
     * If the currently logged user is a {@link Consulente consulente}, the list of {@link Dipendente dipendenti} returned contains all the dipendenti associated with the aziende associated with the logged consulente.
     *
     * @return a list of {@link Dipendente dipendenti} in the database with an HttpStatus based on the outcome
     */
    public List<Dipendente> getDipendenti(){return null;}

    /**
     * Returns a list of {@link Dipendente dipendenti} in the database associated with the {@link Azienda azienda} with the given Partita Iva.
     *
     * @param pIva a String containing the Partita Iva of the {@link Azienda azienda} whose {@link Dipendente dipendenti} should be returned
     *
     * @return a list of {@link Dipendente dipendenti} in the database associated with the given Partita Iva with an HttpStatus based on the outcome
     */
    public ResponseEntity<List<Dipendente>> getDipendentiByPIva(String pIva){return null;}

    /**
     * Returns the {@link Dipendente dipendente} with the given id.
     *
     * @param id a Long containing the id of the {@link Dipendente dipendente} to return
     *
     * @return the {@link Dipendente dipendente} with the given id with an HttpStatus based on the outcome
     */
    public ResponseEntity<Dipendente> getDipendente(Long id){return null;}

    /**
     * Returns the list of {@link Dipendente dipendenti} in the database associated with the {@link Corso corso} with the given id.
     *
     * @param idCorso a Long containing the id of the {@link Corso corso} whose {@link Dipendente dipendenti} should be returned
     *
     * @return a list of {@link Dipendente dipendenti} in the database associated with the given id with an HttpStatus based on the outcome
     */
    public ResponseEntity<List<Dipendente>> getDipendentiByCorso(Long idCorso){return null;}

    /**
     * Returns a list of {@link Dipendente dipendenti} in the database not associated with the {@link Corso corso} with the given id.
     *
     * @param idCorso a Long containing the id of the {@link Corso corso} whose {@link Dipendente dipendenti} should NOT be returned
     *
     * @return a list of {@link Dipendente dipendenti} in the database NOT associated with the given id with an HttpStatus based on the outcome
     */
    public ResponseEntity<List<Dipendente>> getDipendentiNonIscritti(Long idCorso){return null;}

    /**
     * Returns the outcome of the addition of the {@link Dipendente dipendente} in the database.
     * The {@link Dipendente dipendente} is added only if the id is not already present in the database.
     *
     * @param file a MultipartFile containing a list of {@link Dipendente dipendenti} to add
     *             This parameter accepts only CSV and Excel files.
     * @param fileType a String containing the type of the file
     *                 This parameter accepts only "xlsx" and "csv" values.
     *
     * @return the outcome of the addition of the {@link Dipendente dipendente} in the database as an HttpStatus
     */
    public ResponseEntity<String> aggiungiDipendentiDaFile(MultipartFile file, String fileType){
        return switch (fileType) {
            case "xlsx" -> aggiungiDipendentiDaExcel(file);
            case "csv" -> aggiungiDipendentiDaCsv(file);
            default -> new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        };
    }

    public ResponseEntity<String> aggiungiDipendentiDaExcel(MultipartFile file){return null;}

    public ResponseEntity<String> aggiungiDipendentiDaCsv(MultipartFile file){return null;}

    /**
     * Returns the outcome of the addition of the {@link Dipendente dipendente} in the database.
     * The {@link Dipendente dipendente} is added only if the Codice Fiscale is not already present in the database.
     * The {@link Dipendente dipendente} is added only if the email is not already present in the database.
     * The {@link Dipendente dipendente} is added only if the phone number is not already present in the database.
     *
     * @param json a MultipartFile containing the JSON of the {@link Dipendente dipendente} to add
     * @param file A MultipartFile containing the image of the {@link Dipendente dipendente} to add
     *             This parameter accepts null values.
     *
     * @return the outcome of the addition of the {@link Dipendente dipendente} in the database as an HttpStatus
     */
    public ResponseEntity<String> aggiungiDipendete(MultipartFile json, MultipartFile file){return null;}

    /**
     * Returns the outcome of the modification of the {@link Dipendente dipendente} in the database.
     *
     * @param json a MultipartFile containing the JSON of the {@link Dipendente dipendente} to modify
     * @param file A MultipartFile containing the image of the {@link Dipendente dipendente} to modify
     *             This parameter accepts null values.
     *
     * @return the outcome of the modification of the {@link Dipendente dipendente} in the database as an HttpStatus
     */
    public ResponseEntity<String> modificaDipendente(MultipartFile json, MultipartFile file){return null;}

    /**
     * Returns the outcome of the removal of the {@link Dipendente dipendente} in the database.
     * The {@link Dipendente dipendente} is removed only if the id is already present in the database.
     *
     * @param id a Long containing the id of the {@link Dipendente dipendente} to remove
     *
     * @return the outcome of the removal of the {@link Dipendente dipendente} in the database as an HttpStatus
     */
    public ResponseEntity<String> rimuoviDipendente(Long id){return null;}

    /**
     * Returns the outcome of the removal of the image of the {@link Dipendente dipendente} in the database.
     * The image is removed only if the id is already present in the database.
     *
     * @param id a Long containing the id of the {@link Dipendente dipendente} whose image should be removed.
     *
     * @return the outcome of the removal of the image of the {@link Dipendente dipendente} in the database as an HttpStatus
     */
    public ResponseEntity<String> rimuoviImmagineDipendente(Long id){return null;}




    // Documento Service

    /**
     * Returns a list of {@link Documento documenti} in the database associated with the {@link Azienda azienda} with the given Partita Iva.
     *
     * @param pIva a String containing the Partita Iva of the {@link Azienda azienda} whose {@link Documento documenti} should be returned
     *
     * @return a list of {@link Documento documenti} in the database associated with the given Partita Iva with an HttpStatus based on the outcome
     */
    public ResponseEntity<List<Documento>> getDocumentiAzienda(String pIva){return null;}

    /**
     * Returns a list of {@link Documento documenti} in the database associated with the {@link Dipendente dipendente} with the given id.
     *
     * @param id a Long containing the id of the {@link Dipendente dipendente} whose {@link Documento documenti} should be returned
     *
     * @return a list of {@link Documento documenti} in the database associated with the given id with an HttpStatus based on the outcome
     */
    public ResponseEntity<List<Documento>> getDocumentiDipendente(Long id){return null;}

    /**
     * Returns the {@link Documento documento} with the given id.
     *
     * @param id a Long containing the id of the {@link Documento documento} to return
     *
     * @return the {@link Documento documento} with the given id with an HttpStatus based on the outcome
     */
    public ResponseEntity<Documento> getDocumento(Long id){return null;}

    /**
     * Returns a list of {@link Documento documenti} in the database associated with the {@link Azienda azienda} with the given Partita Iva that are in the "Valido" state.
     *
     * @param pIva a String containing the Partita Iva of the {@link Azienda azienda} whose {@link Documento documenti} should be returned
     *
     * @return a list of {@link Documento documenti} in the database associated with the given Partita Iva that are in the "Valido" state with an HttpStatus based on the outcome
     */
    public ResponseEntity<List<Documento>> getDocumentiValidi(String pIva){return null;}

    /**
     * Returns a list of {@link Documento documenti} in the database associated with the {@link Azienda azienda} with the given Partita Iva that are in the "In Scadenza" state.
     *
     * @param pIva a String containing the Partita Iva of the {@link Azienda azienda} whose {@link Documento documenti} should be returned
     *
     * @return a list of {@link Documento documenti} in the database associated with the given Partita Iva that are in the "In Scadenza" state with an HttpStatus based on the outcome
     */
    public ResponseEntity<List<Documento>> getDocumentiInScadenza(String pIva){return null;}

    /**
     * Returns a list of {@link Documento documenti} in the database associated with the {@link Azienda azienda} with the given Partita Iva that are in the "Scaduto" state.
     *
     * @param pIva a String containing the Partita Iva of the {@link Azienda azienda} whose {@link Documento documenti} should be returned
     *
     * @return a list of {@link Documento documenti} in the database associated with the given Partita Iva that are in the "Scaduto" state with an HttpStatus based on the outcome
     */
    public ResponseEntity<List<Documento>> getDocumentiScaduti(String pIva){return null;}

    /**
     * Returns a list of {@link Documento documenti} in the database associated with the {@link Azienda azienda} with the given Partita Iva that are in the given state.
     *
     * @param pIva a String containing the Partita Iva of the {@link Azienda azienda} whose {@link Documento documenti} should be returned
     * @param stato a String containing the state of the {@link Documento documenti} to return
     *              This parameter accepts only "Valido", "In Scadenza" and "Scaduto" values.
     *
     * @return a list of {@link Documento documenti} in the database associated with the given Partita Iva that are in the given state with an HttpStatus based on the outcome
     */
    public ResponseEntity<List<Documento>> getDocumentiByStato(String pIva, String stato){

            List<Documento> documenti = new ArrayList<>(DBManager.getInstance().getDocumentoDao()
                    .findByAzienda(pIva)
                    .stream()
                    .filter(d -> stato.equals(d.getStato()))
                    .toList());

            List<Dipendente> dipendenti = DBManager.getInstance().getDipendenteDao().findByAzienda(pIva);

            for (Dipendente d : dipendenti) {
                List<Documento> documentiDipendente = DBManager.getInstance().getDocumentoDao().findByDipendente(d.getId())
                        .stream()
                        .filter(doc -> stato.equals(doc.getStato()))
                        .toList();
                documenti.addAll(documentiDipendente);
            }
            
            return new ResponseEntity<>(documenti, HttpStatus.OK);
    }

    /**
     * Returns the outcome of the addition of the {@link Documento documento} in the database.
     *
     * @param json a MultipartFile containing the JSON of the {@link Documento documento} to add
     * @param file A MultipartFile containing the file of the {@link Documento documento} to add
     * @param pIva a String containing the Partita Iva of the {@link Azienda azienda} to which the {@link Documento documento} should be added
     *             This parameter accepts null values.
     * @param cf a String containing the Codice Fiscale of the {@link Dipendente dipendente} to which the {@link Documento documento} should be added
     *             This parameter accepts null values.
     * @implNote If both pIva and cf are null, the method returns an HttpStatus.BAD_REQUEST.
     *
     * @return the outcome of the addition of the {@link Documento documento} in the database as an HttpStatus
     */
    public ResponseEntity<String> aggiungiDocumento(MultipartFile json, MultipartFile file, String pIva, String cf){return null;}

    /**
     * Returns the outcome of the addition of the {@link Documento documento} associated with the {@link Azienda azienda} with the given Partita Iva in the database.
     *
     * @param json a MultipartFile containing the JSON of the {@link Documento documento} to add
     * @param file a MultipartFile containing the file of the {@link Documento documento} to add
     * @param pIva a String containing the Partita Iva of the {@link Azienda azienda} whose {@link Documento documento} should be added
     *
     * @return the outcome of the addition of the {@link Documento documento} in the database as an HttpStatus
     */
    public ResponseEntity<String> aggiungiDocumentoAzienda(MultipartFile json, MultipartFile file, String pIva) {
        return aggiungiDocumento(json, file, pIva, null);
    }

    /**
     * Returns the outcome of the addition of the {@link Documento documento} associated with the {@link Dipendente dipendente} with the given Codice Fiscale in the database.
     *
     * @param json a MultipartFile containing the JSON of the {@link Documento documento} to add
     * @param file a MultipartFile containing the file of the {@link Documento documento} to add
     * @param cf a String containing the Codice Fiscale of the {@link Dipendente dipendente} whose {@link Documento documento} should be added
     *
     * @return the outcome of the addition of the {@link Documento documento} in the database as an HttpStatus
     */
    public ResponseEntity<String> aggiungiDocumentoDipendente(MultipartFile json, MultipartFile file, String cf) {
        return aggiungiDocumento(json, file, null, cf);
    }

    /**
     * Returns the outcome of the modification of the {@link Documento documento} in the database.
     *
     * @param json a MultipartFile containing the JSON of the {@link Documento documento} to modify
     * @param file A MultipartFile containing the file of the {@link Documento documento} to modify
     * @param pIva a String containing the Partita Iva of the {@link Azienda azienda} whose {@link Documento documento} should be modified
     *             This parameter accepts null values.
     * @param cf a String containing the Codice Fiscale of the {@link Dipendente dipendente} whose {@link Documento documento} should be modified
     *             This parameter accepts null values.
     * @implNote If both pIva and cf are null, the method returns an HttpStatus.BAD_REQUEST.
     *
     * @return the outcome of the modification of the {@link Documento documento} in the database as an HttpStatus
     */
    public ResponseEntity<String> modificaDocumento(MultipartFile json, MultipartFile file, String pIva, String cf) {return null;}

    /**
     * Returns the outcome of the update of the {@link Documento documento} associated with the {@link Azienda azienda} with the given Partita Iva in the database.
     *
     * @param json a MultipartFile containing the JSON of the {@link Documento documento} to modify
     * @param file a MultipartFile containing the file of the {@link Documento documento} to modify
     * @param pIva a String containing the Partita Iva of the {@link Azienda azienda} whose {@link Documento documento} should be modified
     *
     * @return the outcome of the modification of the {@link Documento documento} in the database as an HttpStatus
     */
    public ResponseEntity<String> modificaDocumentoAzienda(MultipartFile json, MultipartFile file, String pIva) {
        return modificaDocumento(json, file, pIva, null);
    }

    /**
     * Returns the outcome of the update of the {@link Documento documento} associated with the {@link Dipendente dipendente} with the given Codice Fiscale in the database.
     *
     * @param json a MultipartFile containing the JSON of the {@link Documento documento} to modify
     * @param file a MultipartFile containing the file of the {@link Documento documento} to modify
     * @param cf a String containing the Codice Fiscale of the {@link Dipendente dipendente} whose {@link Documento documento} should be modified
     *
     * @return the outcome of the modification of the {@link Documento documento} in the database as an HttpStatus
     */
    public ResponseEntity<String> modificaDocumentoDipendente(MultipartFile json, MultipartFile file, String cf) {
        return modificaDocumento(json, file, null, cf);
    }

    /**
     * Returns the outcome of the removal of the {@link Documento documento} in the database.
     *
     * @param id a Long containing the id of the {@link Documento documento} to remove
     *
     * @return the outcome of the removal of the {@link Documento documento} in the database as an HttpStatus
     */
    public ResponseEntity<String> rimuoviDocumento(Long id){return null;}




    // Ricerca Service

    /**
     * Returns a {@link Ricerca ricerca} containing a List of {@link Azienda aziende}, a  List of {@link Dipendente dipendenti} and a List of {@link Documento documenti} that match the given query.
     *
     * @param pIva a String containing the Partita Iva of the {@link Azienda azienda} to which the {@link Ricerca ricerca} should be limited
     * @param q a String containing the words to search separated by spaces
     *
     * @return a {@link Ricerca ricerca} that match the given query with an HttpStatus based on the outcome
     */
    public ResponseEntity<Ricerca> ricerca(String pIva, String q) {return null;}
}
