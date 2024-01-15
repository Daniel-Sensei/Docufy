import { Component } from '@angular/core';
import { Azienda } from '../../../model/Azienda';
import { Documento } from '../../../model/Documento';
import { Dipendente } from '../../../model/Dipendente';

import { ActivatedRoute } from '@angular/router';
import { CommunicationService } from '../../../service/communication/communication.service';
import { RicercaService } from '../../../service/ricerca/ricerca.service';
import { AuthService } from '../../../service/auth/auth.service';

@Component({
  selector: 'app-ricerca',
  templateUrl: './ricerca.component.html',
  styleUrl: './ricerca.component.css'
})
export class RicercaComponent {

  constructor(
    private route: ActivatedRoute,
    private communication: CommunicationService,
    private ricercaService: RicercaService,
    private auth: AuthService) { }

  aziende?: Azienda[];
  documenti?: Documento[];
  dipendenti?: Dipendente[];

  pIva!:string

  ngOnInit(): void {
    if (this.auth.getRole() == 'A') {
      this.pIva = this.auth.getCurrentPIva()!;
    }
    else {
      this.pIva = this.auth.getSelectedPIva()!;
    }

    const text = this.route.snapshot.paramMap.get('text');
    this.search(text || '');
  
    // Iscriviti agli aggiornamenti del testo di ricerca
    this.communication.searchTextChanged$.subscribe((searchText) => {
      this.search(searchText);
    });
  }

  search(text: string) {
    this.clear();
    console.log(text);
    this.ricercaService.search(this.pIva, text).subscribe((data) => {
      this.aziende = data.aziende;
      this.documenti = data.documenti;
      this.dipendenti = data.dipendenti;
    });
  }

  clear() {
    this.aziende = undefined;
    this.documenti = undefined;
    this.dipendenti = undefined;
  }


}
