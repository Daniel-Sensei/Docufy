import { Component } from '@angular/core';
import { Azienda } from '../../../model/Azienda';
import { Documento } from '../../../model/Documento';
import { Dipendente } from '../../../model/Dipendente';

@Component({
  selector: 'app-ricerca',
  templateUrl: './ricerca.component.html',
  styleUrl: './ricerca.component.css'
})
export class RicercaComponent {

  aziende: Azienda[] = [];
  documenti: Documento[] = [];
  dipendenti: Dipendente[] = [];

}
