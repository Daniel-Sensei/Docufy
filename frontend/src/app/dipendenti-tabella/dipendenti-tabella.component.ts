import { Component } from '@angular/core';

import { Dipendente } from '../model/Dipendente';
import { DipendentiService } from '../service/dipendenti.service';

import {Sort} from '@angular/material/sort';

@Component({
  selector: 'app-dipendenti-tabella',
  templateUrl: './dipendenti-tabella.component.html',
  styleUrl: './dipendenti-tabella.component.css'
})
export class DipendentiTabellaComponent {

  dipendenti: Dipendente[] = [];
  sortedData: Dipendente[] = [];

  constructor(private dipendentiService: DipendentiService,) {}


  ngOnInit(): void {
    this.dipendentiService.getAllDipendenti().subscribe(dipendenti => {
      this.dipendenti = dipendenti;
      this.sortedData = this.dipendenti.slice(); // Aggiungi questo per assicurarti che sortedData sia inizializzato correttamente
    });
  }
  
  

  sortData(sort: Sort) {
    const compare = (a: number | string, b: number | string, isAsc: boolean): number => {
      if (a < b) {
        return isAsc ? -1 : 1;
      } else if (a > b) {
        return isAsc ? 1 : -1;
      } else {
        return 0;
      }
    };

    const data = this.dipendenti.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedData = data;
      return;
    }

    this.sortedData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'cognome': return compare(a.cognome, b.cognome, isAsc);
        case 'ruolo': return compare(a.ruolo, b.ruolo, isAsc);
        case 'dataAssunzione': return compare(a.dataAssunzione, b.dataAssunzione, isAsc);
        default: return 0;
      }
    });
  }
}

