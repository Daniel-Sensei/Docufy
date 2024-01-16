package com.exam.esameweb24_backend.controller;

import com.exam.esameweb24_backend.persistence.model.Dipendente;
import com.exam.esameweb24_backend.persistence.model.Documento;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Utility {

    /**
     * The directory where the uploaded files are stored
     */
    private static final String uploadDir = "./backend/src/main/resources/files";

    /**
     * Encodes the given value in Base64
     *
     * @param value the String to encode
     *
     * @return the encoded String
     */
    public static String encodeBase64(String value){
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

    /**
     * Decodes the given value from Base64
     *
     * @param value the String to decode
     *
     * @return the decoded String
     */
    public static String decodeBase64(String value){
        return new String(Base64.getDecoder().decode(value.getBytes()));
    }

    /**
     * Returns the token from the given {@link HttpServletRequest request}
     *
     * @param req the {@link HttpServletRequest request} from which to get the token
     *
     * @return the token from the given {@link HttpServletRequest request}
     */
    public static String getToken(HttpServletRequest req){
        String auth = req.getHeader("Authorization");
        if (auth == null) return "";
        return auth.substring("Basic ".length());
    }

    /**
     * Returns the email from the given token
     *
     * @param token the token from which to get the email
     *
     * @return the email from the given token
     */
    public static String getTokenEmail(String token){
        return decodeBase64(token).split(":tkn:")[0];
    }

    /**
     * Returns the password from the given token
     *
     * @param token the token from which to get the password
     *
     * @return the password from the given token
     */
    public static String getTokenPassword(String token){
        return decodeBase64(token).split(":tkn:")[1];
    }

    /**
     * Returns the role from the given token
     *
     * @param token the token from which to get the role
     *
     * @return the role from the given token
     */
    public static String getTokenRole(String token){
        return decodeBase64(token).split(":tkn:")[2];
    }

    /**
     * Checks if the given token belongs to a Consulente or not
     *
     * @param token the token to check
     *
     * @return true if the given token belongs to a Consulente, false otherwise
     */
    public static Boolean isConsultant(String token) {
        return getTokenRole(token).equals("C");
    }

    /**
     * Checks if the given consulente is associated with the given azienda
     *
     * @param consultant the Partita IVA of the consulente to check
     * @param agency the Partita IVA of the azienda to check
     *
     * @return true if the given consulente is associated with the given azienda, false otherwise
     */
    public static Boolean checkConsultantAgency(String consultant, String agency) {
        return (DBManager.getInstance().getAziendaDao().findByConsultant(consultant).stream().anyMatch(azienda -> azienda.getPIva().equals(agency)));
    }

    /**
     * Checks if the given azienda is associated with the given dipendente
     *
     * @param agency the Partita IVA of the azienda to check
     * @param employee the ID of the dipendente to check
     *
     * @return true if the given azienda is associated with the given dipendente, false otherwise
     */
    public static Boolean checkAgencyEmployeeID(String agency, Long employee) {
        return (DBManager.getInstance().getDipendenteDao().findById(employee).getAzienda().getPIva().equals(agency));
    }

    /**
     * Checks if the given azienda is associated with the given dipendente
     *
     * @param agency the Partita IVA of the azienda to check
     * @param cf the Codice Fiscale of the dipendente to check
     *
     * @return true if the given azienda is associated with the given dipendente, false otherwise
     */
    public static Boolean checkAgencyEmployeeCF(String agency, String cf) {
        return (DBManager.getInstance().getDipendenteDao().findByCF(cf).getAzienda().getPIva().equals(agency));
    }

    /**
     * Check if the password is correct
     *
     * @param plainPW the password to check
     *                (not encrypted)
     * @param storedUser the user from which to get the password
     *                   (encrypted)
     * @return true if the password is correct, false otherwise
     */
    public static boolean checkPassword(String plainPW, User storedUser){
        return BCrypt.checkpw(plainPW, storedUser.getPassword());
    }

    /**
     * Returns the {@link User user} from the given {@link HttpServletRequest request}
     *
     * @param req the {@link HttpServletRequest request} from which to get the {@link User user}
     *
     * @return the {@link User user} from the given {@link HttpServletRequest request}
     */
    public static User getRequestUser(HttpServletRequest req){
        return DBManager.getInstance().getUserDao().findByToken(getToken(req));
    }

    /**
     * Generates a name for the given file based on the given prefix and the current time
     *
     * @param prefix the prefix of the file
     *               (This is usually the Partita IVA of the {@link User user} who uploaded the file or the Codice Fiscale of the {@link Dipendente dipendente} to which the file belongs to)
     * @param file the file from which to get the extension
     *
     * @return the name for the given file
     */
    public static String generateFileName(String prefix, MultipartFile file) {
        // prende il nome del file originale
        String originalFileName = file.getOriginalFilename();
        // si assicura che il nome non sia nullo
        assert originalFileName != null;
        // si assicura che il file abbia un'estensione
        assert originalFileName.contains(".");
        // prende l'estensione del file
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        // restituisce il nuovo nome del file
        return prefix + "_" + System.currentTimeMillis() + extension;
    }

    /**
     * Converts the given file to an object of the given type
     *
     * @param file the file to convert
     *             (Must be a JSON file)
     * @param valueType the type of the object to convert to
     *
     * @return the object of the given type converted from the given JSON
     */
    public static <T> T jsonToObject(MultipartFile file, Class<T> valueType) {
        try {
            byte[] fileBytes = file.getBytes();
            String jsonString = new String(fileBytes);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Uploads the given file to the server
     *
     * @param pIva the Partita IVA of the {@link User user} who uploaded the file
     *             (Can be the Codice Fiscale of the {@link Dipendente dipendente} to which the file belongs to)
     * @param file the file to upload
     *
     * @return the path of the uploaded file
     *
     * @throws IOException
     */
    public static String uploadFile(String pIva, MultipartFile file) throws IOException {
        String fileName = Utility.generateFileName(pIva, file);
        String filePath = uploadDir + "/" + fileName;

        Path destination = Paths.get(filePath);
        Files.write(destination, file.getBytes());

        return filePath;
    }

    /**
     * Deletes the file at the given path
     *
     * @param path the path of the file to delete
     *
     * @return true if the file was deleted, false otherwise
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (file.exists())
            return file.delete();
        return false;
    }

    /**
     * Returns the state of the given {@link Documento documento}
     *
     * @param documento a {@link Documento documento} whose state should be calculated
     *
     * @return a String containing the state of the given {@link Documento documento}
     */
    public static String setDataDocumento(Documento documento) {
        // controllo lo stato del documento: Scaduto, Valido o In Scadenza (1 MESE PRIMA SCADENZA)
        if (documento.getDataScadenza().before(new Date())) return "Scaduto";
        else if (documento.getDataScadenza().before(new Date(new Date().getTime() + 2592000000L))) return "In Scadenza";
        else return "Valido";
    }

    /**
     * Updates the state of all the {@link Documento documenti} in the database
     */
    public static void updateAllDocumentsState(){

        List<Documento> inScadenza = new ArrayList<>();
        List<Documento> scaduto = new ArrayList<>();

        DBManager.getInstance().getDocumentoDao().getAll().forEach(documento -> {

            if(documento.getDataScadenza().getTime() - System.currentTimeMillis() > 2592000000L)
                documento.setStato("Valido");
            else if(documento.getDataScadenza().getTime() - System.currentTimeMillis() > 0) {
                documento.setStato("In Scadenza");
                inScadenza.add(documento);
            }
            else if (documento.getDataScadenza().getTime() - System.currentTimeMillis() <= 0) {
                documento.setStato("Scaduto");
                scaduto.add(documento);
            }
            DBManager.getInstance().getDocumentoDao().update(documento);
        });

        while (!inScadenza.isEmpty() || !scaduto.isEmpty()) {

            List<Documento> inScadenzaSend = new ArrayList<>();
            List<Documento> scadutoSend = new ArrayList<>();

            String emailTo = "";
            String[] cc = new String[2];
            String subject = "Notifica Scadenza Documenti";

            if (!inScadenza.isEmpty()) {
                inScadenzaSend.add(inScadenza.get(0));
                inScadenza.remove(0);
                if(inScadenzaSend.get(0).getAzienda()!=null) {
                    emailTo = inScadenzaSend.get(0).getAzienda().getEmail();
                    cc[0] = inScadenzaSend.get(0).getAzienda().getEmail();
                    cc[1] = inScadenzaSend.get(0).getAzienda().getConsulente().getEmail();
                } else {
                    emailTo = inScadenzaSend.get(0).getDipendente().getAzienda().getEmail();
                    cc[0] = inScadenzaSend.get(0).getDipendente().getAzienda().getEmail();
                    cc[1] = inScadenzaSend.get(0).getDipendente().getAzienda().getConsulente().getEmail();
                }

                Iterator<Documento> iterator = inScadenza.iterator();
                if (iterator.hasNext()) {
                    do {
                        Documento documento = iterator.next();
                        if (inScadenzaSend.get(0).getAzienda()!=null && documento.getAzienda() != null && inScadenzaSend.get(0).getAzienda().equals(documento.getAzienda())) {
                            inScadenzaSend.add(documento);
                            iterator.remove(); // Rimuove l'elemento corrente in modo sicuro
                        } else if (inScadenzaSend.get(0).getDipendente()!=null && documento.getDipendente() != null && inScadenzaSend.get(0).getDipendente().getAzienda().equals(documento.getDipendente().getAzienda())) {
                            inScadenzaSend.add(documento);
                            iterator.remove(); // Rimuove l'elemento corrente in modo sicuro
                        } else if (inScadenzaSend.get(0).getDipendente()!=null && documento.getAzienda() != null && inScadenzaSend.get(0).getDipendente().getAzienda().equals(documento.getAzienda())) {
                            inScadenzaSend.add(documento);
                            iterator.remove(); // Rimuove l'elemento corrente in modo sicuro
                        } else if (inScadenzaSend.get(0).getAzienda()!=null && documento.getDipendente() != null && inScadenzaSend.get(0).getAzienda().equals(documento.getDipendente().getAzienda())) {
                            inScadenzaSend.add(documento);
                            iterator.remove(); // Rimuove l'elemento corrente in modo sicuro
                        }
                    } while (iterator.hasNext());
                }

                Iterator<Documento> iterator2 = scaduto.iterator();
                if (iterator2.hasNext()) {
                    do {
                        Documento documento = iterator2.next();
                        if (inScadenzaSend.get(0).getAzienda()!=null && documento.getAzienda() != null && inScadenzaSend.get(0).getAzienda().equals(documento.getAzienda())) {
                            scadutoSend.add(documento);
                            iterator2.remove(); // Rimuove l'elemento corrente in modo sicuro
                        } else if (inScadenzaSend.get(0).getDipendente()!=null && documento.getDipendente() != null && inScadenzaSend.get(0).getDipendente().getAzienda().equals(documento.getDipendente().getAzienda())) {
                            scadutoSend.add(documento);
                            iterator2.remove(); // Rimuove l'elemento corrente in modo sicuro
                        } else if (inScadenzaSend.get(0).getDipendente()!=null && documento.getAzienda() != null && inScadenzaSend.get(0).getDipendente().getAzienda().equals(documento.getAzienda())) {
                            scadutoSend.add(documento);
                            iterator2.remove(); // Rimuove l'elemento corrente in modo sicuro
                        } else if (inScadenzaSend.get(0).getAzienda()!=null && documento.getDipendente() != null && inScadenzaSend.get(0).getAzienda().equals(documento.getDipendente().getAzienda())) {
                            scadutoSend.add(documento);
                            iterator2.remove(); // Rimuove l'elemento corrente in modo sicuro
                        }
                    } while (iterator2.hasNext());
                }

            } else {
                scadutoSend.add(scaduto.get(0));
                scaduto.remove(0);
                if(scadutoSend.get(0).getAzienda()!=null) {
                    emailTo = scadutoSend.get(0).getAzienda().getEmail();
                    cc[0] = scadutoSend.get(0).getAzienda().getEmail();
                    cc[1] = scadutoSend.get(0).getAzienda().getConsulente().getEmail();
                } else {
                    emailTo = scadutoSend.get(0).getDipendente().getAzienda().getEmail();
                    cc[0] = scadutoSend.get(0).getDipendente().getAzienda().getEmail();
                    cc[1] = scadutoSend.get(0).getDipendente().getAzienda().getConsulente().getEmail();
                }

                // Creazione di un iteratore per evitare IndexOutOfBoundsException
                Iterator<Documento> iterator = scaduto.iterator();
                if (iterator.hasNext()) {
                    do {
                        Documento documento = iterator.next();
                        if (scadutoSend.get(0).getAzienda()!=null && documento.getAzienda() != null && scadutoSend.get(0).getAzienda().equals(documento.getAzienda())) {
                            scadutoSend.add(documento);
                            iterator.remove(); // Rimuove l'elemento corrente in modo sicuro
                        } else if (scadutoSend.get(0).getDipendente()!=null && documento.getDipendente() != null && scadutoSend.get(0).getDipendente().getAzienda().equals(documento.getDipendente().getAzienda())) {
                            scadutoSend.add(documento);
                            iterator.remove(); // Rimuove l'elemento corrente in modo sicuro
                        } else if (scadutoSend.get(0).getDipendente()!=null && documento.getAzienda() != null && scadutoSend.get(0).getDipendente().getAzienda().equals(documento.getAzienda())) {
                            scadutoSend.add(documento);
                            iterator.remove(); // Rimuove l'elemento corrente in modo sicuro
                        } else if (scadutoSend.get(0).getAzienda()!=null && documento.getDipendente() != null && scadutoSend.get(0).getAzienda().equals(documento.getDipendente().getAzienda())) {
                            scadutoSend.add(documento);
                            iterator.remove(); // Rimuove l'elemento corrente in modo sicuro
                        }
                    } while (iterator.hasNext());
                }
            }

            EmailSender.sendDocumentExpirationMail(emailTo, cc, subject, inScadenzaSend, scadutoSend);
        }
    }
}
