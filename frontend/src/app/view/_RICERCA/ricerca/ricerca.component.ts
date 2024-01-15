import { Component } from '@angular/core';
import { Azienda } from '../../../model/Azienda';
import { Documento } from '../../../model/Documento';
import { Dipendente } from '../../../model/Dipendente';

import { ActivatedRoute } from '@angular/router';
import { CommunicationService } from '../../../service/communication/communication.service';
import { RicercaService } from '../../../service/ricerca/ricerca.service';

@Component({
  selector: 'app-ricerca',
  templateUrl: './ricerca.component.html',
  styleUrl: './ricerca.component.css'
})
export class RicercaComponent {

  constructor(
    private route: ActivatedRoute,
    private communication: CommunicationService,
    private ricercaService: RicercaService) { }

  aziende?: Azienda[];
  documenti?: Documento[];
  dipendenti?: Dipendente[];

  ngOnInit(): void {
    const text = this.route.snapshot.paramMap.get('text');
    this.search(text || '');
  
    // Iscriviti agli aggiornamenti del testo di ricerca
    this.communication.searchTextChanged$.subscribe((searchText) => {
      this.search(searchText);
      console.log('Search text changed:', searchText);
    });
  }

  search(text: string) {
    console.log(text);
    this.ricercaService.search(text).subscribe((data) => {
      console.log(data);
      this.aziende = data.aziende;
      this.documenti = data.documenti;
      this.dipendenti = data.dipendenti;
    });
  }

}
