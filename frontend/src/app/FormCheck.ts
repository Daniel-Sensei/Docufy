import { NgbDate } from '@ng-bootstrap/ng-bootstrap';

export class FormCheck {
    static checkNome(nome: string): boolean {
        const regex = /^[a-zA-Z ]+$/;
        return regex.test(nome);
    }

    static checkTelefono(telefono: string): boolean {
        const regex = /^\+?\d+ ?\d+$/;
        return regex.test(telefono);
    }

    static checkEmail(email: string): boolean {
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return regex.test(email);
    }

    static checkCodiceFiscale(cf: string): boolean {
        const regex = /[a-zA-Z]{6}\d{2}[a-zA-Z]\d{2}[a-zA-Z]\d{3}[a-zA-Z]/;
        return regex.test(cf);
    }

    static dataNascitaMinima(dataNascita: NgbDate): boolean{
        const today = new Date();
        const minAgeDate = new NgbDate(today.getFullYear() - 18, today.getMonth() + 1 , today.getDate());
        
        if(dataNascita.year > minAgeDate.year || 
            (dataNascita.year === minAgeDate.year && dataNascita.month > minAgeDate.month) || 
            (dataNascita.year === minAgeDate.year && dataNascita.month === minAgeDate.month && dataNascita.day > minAgeDate.day)){
                return false;
        }
        return true;
    }

    static compareTwoDates(date1: NgbDate, date2: NgbDate): boolean{
        if(date1.year < date2.year || 
            (date1.year === date2.year && date1.month < date2.month) || 
            (date1.year === date2.year && date1.month === date2.month && date1.day < date2.day)){
                return false;
        }
        return true;
    }
}