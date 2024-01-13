import { Component } from '@angular/core';
import { AziendeService } from '../../../service/aziende/aziende.service';
import { Azienda } from '../../../model/Azienda';
import { Documento } from '../../../model/Documento';
import { DocumentiService } from '../../../service/documenti/documenti.service';
import { AuthService } from '../../../service/auth/auth.service';
import { Corso } from '../../../model/Corso';
import { CorsiService } from '../../../service/corsi/corsi.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  constructor(
    private aziendeService: AziendeService,
    private documentiService: DocumentiService,
    private auth: AuthService,
    private corsiService: CorsiService) { }

  aziende?: Azienda[];
  documentiValidi!: Documento[];
  documentiInScadenza!: Documento[];
  documentiScaduti!: Documento[];

  documentiNonValidi: Documento[] = [];
  documentiInitiated: boolean = false;

  corsi?: Corso[];

  ngOnInit(): void {
    if (this.aziendeService.role == 'C') {
      this.getAziende();
    }

    this.getDocumenti();
    this.getCorsi();
  }

  getAziende(): void {
    this.aziendeService.getAziende().subscribe(aziende => {this.aziende = aziende;});
  }

  getDocumenti(): void {
    var pIva: string = '';
    if (this.auth.getRole() == 'A') {
      pIva = this.auth.getCurrentPIva()!;
    }
    else {
      pIva = this.auth.getSelectedPIva()!;
    }

    this.documentiService.getDocumentiValidi(pIva).subscribe(
      documentiValidi => {
        this.documentiValidi = documentiValidi;
        this.documentiService.getDocumentiInScadenza(pIva).subscribe(
          documentiInScadenza => {
            this.documentiInScadenza = documentiInScadenza;
            this.documentiService.getDocumentiScaduti(pIva).subscribe(
              documentiScaduti => {
                this.documentiScaduti = documentiScaduti;
                //aggiungi a this.documentiNonValidi tutti i documenti che non sono validi (in scadenza e scaduti)
                this.documentiNonValidi = this.documentiNonValidi.concat(this.documentiInScadenza);
                this.documentiNonValidi = this.documentiNonValidi.concat(this.documentiScaduti);
                //ordina i documenti per data di scadenza
                this.documentiNonValidi.sort((a, b) => (a.dataScadenza > b.dataScadenza) ? 1 : -1);
                this.documentiInitiated = true;
              },);
          },);
      },);
  }

  getCorsi(): void {
    var pIva: string = '';
    if (this.auth.getRole() == 'A') {
      pIva = this.auth.getCurrentPIva()!;
    }
    else {
      pIva = this.auth.getSelectedPIva()!;
    }

    this.corsiService.getCorsiAcquistati(pIva).subscribe(corsi => {this.corsi = corsi;});
  }

  updateCorsi(){
    this.corsi = undefined;
    this.getCorsi();
  }

}
