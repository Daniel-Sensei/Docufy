package com.exam.esameweb24_backend.controller;

import com.exam.esameweb24_backend.persistence.model.Azienda;
import com.exam.esameweb24_backend.persistence.model.Dipendente;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelReader {

    public static List<Dipendente> excelFileToDipendenti(MultipartFile multipartFile, String pIva) throws IOException, ParseException {
        List<Dipendente> dipendenti = new ArrayList<>();

        try (InputStream inputStream = multipartFile.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Assume che i dati siano nel primo foglio

            Azienda a = new Azienda();
            a.setPIva(pIva);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // Ignora l'intestazione
                    continue;
                }

                Dipendente dipendente = createDipendenteFromRow(row);
                dipendente.setAzienda(a);
                dipendenti.add(dipendente);
            }
        }

        return dipendenti;
    }

    private static Dipendente createDipendenteFromRow(Row row) throws ParseException {
        Dipendente dipendente = new Dipendente();

        // si assume che i dati siano ordinati come segue:
        // Codice Fiscale, Nome, Cognome, Data di nascita, Email, Telefono, Indirizzo, Data di assunzione, Ruolo
        dipendente.setCF(getStringCellValue(row.getCell(0)));
        dipendente.setNome(getStringCellValue(row.getCell(1)));
        dipendente.setCognome(getStringCellValue(row.getCell(2)));
        dipendente.setDataNascita(getDateCellValue(row.getCell(3)));
        dipendente.setEmail(getStringCellValue(row.getCell(4)));
        dipendente.setTelefono(getStringCellValue(row.getCell(5)));
        dipendente.setResidenza(getStringCellValue(row.getCell(6)));
        dipendente.setDataAssunzione(getDateCellValue(row.getCell(7)));
        dipendente.setRuolo(getStringCellValue(row.getCell(8)));

        return dipendente;
    }

    private static String getStringCellValue(Cell cell) {
        if (cell != null) {
            return switch (cell.getCellType()) {
                case STRING -> cell.getStringCellValue();
                case NUMERIC -> String.valueOf(cell.getNumericCellValue());
                default -> null;
            };
        }
        return null;
    }

    private static Date getDateCellValue(Cell cell) throws ParseException {
        if (cell != null) {
            switch (cell.getCellType()) {
                case NUMERIC:
                    return cell.getDateCellValue();
                case STRING:
                    // Tentativo di parsing della data dalla stringa
                    String dateString = cell.getStringCellValue();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    return dateFormat.parse(dateString);
                default:
                    return null;
            }
        }
        return null;
    }
}

