import { Component } from '@angular/core';

import { Dipendente } from '../model/Dipendente';
import { DipendentiService } from '../service/dipendenti.service';

@Component({
  selector: 'app-dipendenti-tabella',
  templateUrl: './dipendenti-tabella.component.html',
  styleUrl: './dipendenti-tabella.component.css'
})
export class DipendentiTabellaComponent {

  constructor(private dipendentiService: DipendentiService) { }

  dipendenti: Dipendente[] = [];

  ngOnInit(): void {
    this.dipendentiService.getAllDipendenti().subscribe(dipendenti => this.dipendenti = dipendenti);
  }

}
