import { Component } from '@angular/core';
import { Dipendente } from '../../../model/Dipendente';

import { ActivatedRoute } from '@angular/router';
import { DipendentiService } from '../../../service/dipendenti.service';

@Component({
  selector: 'app-dipendenti-dettagli',
  templateUrl: './dipendenti-dettagli.component.html',
  styleUrl: './dipendenti-dettagli.component.css'
})
export class DipendentiDettagliComponent {

  dipendente!: Dipendente;

  constructor(
    private route: ActivatedRoute,
    private dipendentiService: DipendentiService,  
  ) {}

  ngOnInit(): void {
    this.getDipendente();
  }

  getDipendente(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    // getDipendente(id: number): void //TODO: da implementare
  }
}
