import { NgbDateParserFormatter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { Injectable } from '@angular/core';

@Injectable()
export class NgbDateITParserFormatter extends NgbDateParserFormatter {
  parse(value: string): NgbDateStruct | null {
    // Implementa la logica di parsing qui
    return null;
  }

  format(date: NgbDateStruct | null): string {
    if (!date) {
      return '';
    }
    
    // Implementa la logica di formattazione qui, ad esempio:
    const day = date.day.toString().padStart(2, '0');
    const month = date.month.toString().padStart(2, '0');
    const year = date.year;

    return `${day}/${month}/${year}`;
  }
}
