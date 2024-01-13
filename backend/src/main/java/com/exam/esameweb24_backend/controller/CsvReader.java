package com.exam.esameweb24_backend.controller;

import com.exam.esameweb24_backend.persistence.model.Azienda;
import com.exam.esameweb24_backend.persistence.model.Dipendente;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsvReader {

    public static List<Dipendente> csvFileToDipendenti(MultipartFile file, String pIva) throws IOException, CsvValidationException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<Dipendente> dipendenti = new ArrayList<>();
            String[] line;
            reader.skip(1); // Salta l'intestazione

            Azienda a = new Azienda();
            a.setPIva(pIva);

            while ((line = reader.readNext()) != null) {
                Dipendente dipendente = parseCsvLine(line);
                if (dipendente != null) {
                    dipendente.setAzienda(a);
                    dipendenti.add(dipendente);
                }
            }

            return dipendenti;
        }
    }

    private static Dipendente parseCsvLine(String[] line) {
        try {
            Dipendente dipendente = new Dipendente();
            dipendente.setCF(line[0]);
            dipendente.setNome(line[1]);
            dipendente.setCognome(line[2]);
            dipendente.setDataNascita(parseDate(line[3]));
            dipendente.setEmail(line[4]);
            dipendente.setTelefono(line[5]);
            dipendente.setResidenza(line[6]);
            dipendente.setDataAssunzione(parseDate(line[7]));
            dipendente.setRuolo(line[8]);

            return dipendente;
        } catch (Exception e) {
            // Gestisci eventuali eccezioni durante il parsing
            return null;
        }
    }

    private static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateString);
    }
}
