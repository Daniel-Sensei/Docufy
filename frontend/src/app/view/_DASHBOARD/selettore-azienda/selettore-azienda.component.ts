// Nel tuo componente Angular
import { Component, Input } from '@angular/core';
import { Azienda } from '../../../model/Azienda';
import { AuthService } from '../../../service/auth/auth.service';

@Component({
  selector: 'app-selettore-azienda',
  templateUrl: './selettore-azienda.component.html',
  styleUrls: ['./selettore-azienda.component.css']
})
export class SelettoreAziendaComponent {

  constructor(private auth: AuthService) { }

  @Input() aziende?: Azienda[];
  aziendaSelezionata: string = '';  // Aggiunto per memorizzare la selezione

  ngOnInit() {
    // Imposta la prima azienda come predefinita se ci sono aziende disponibili
    if (this.aziende && this.aziende.length > 0) {
      let pIva = this.auth.getSelectedPIva();
      if (pIva) {
        let index = this.aziende.findIndex(azienda => azienda.piva === pIva);
        if (index >= 0) {
          this.aziendaSelezionata = this.aziende[index].ragioneSociale;
          this.auth.setSelectedPIva(this.aziende[index].piva);
        }
      }
      else {
        this.aziendaSelezionata = this.aziende[0].ragioneSociale;
        this.auth.setSelectedPIva(this.aziende[0].piva);
      }
    }
  }

  // Funzione per gestire il cambiamento nella selezione
  onAziendaChange() {
    let index = this.aziende?.findIndex(azienda => azienda.ragioneSociale === this.aziendaSelezionata);
    if (index !== undefined && index >= 0) {
      this.auth.setSelectedPIva(this.aziende![index].piva);
    }
  }
}
