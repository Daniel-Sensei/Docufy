import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';

import { DipendentiService } from '../../../service/dipendenti/dipendenti.service';
import { AlertService } from '../../../service/alert/alert.service';
import { Dipendente } from '../../../model/Dipendente';

import { Documento } from '../../../model/Documento';
import { DocumentiService } from '../../../service/documenti/documenti.service';
import { Output, EventEmitter } from '@angular/core';
import { Azienda } from '../../../model/Azienda';
import { AziendeService } from '../../../service/aziende/aziende.service';

@Component({
  selector: 'app-confirm-modal',
  templateUrl: './confirm-modal.component.html',
  styleUrl: './confirm-modal.component.css'
})
export class ConfirmModalComponent {
  @Output() refreshData: EventEmitter<void> = new EventEmitter<void>();


  @Input() function?: string;
  @Input() dipendente?: Dipendente;
  @Input() documento?: Documento;
  @Input() azienda?: Azienda;

  constructor(
    public activeModal: NgbActiveModal,
    private router: Router,
    private alert: AlertService,
    private dipendentiService: DipendentiService,
    private documentiService: DocumentiService,
    private aziendeService: AziendeService) { }

  submit() {
    if (this.function == 'deleteImgDipendente') {
      this.deleteImgDipendente();
    }
    else if (this.function == 'deleteDipendente') {
      this.deleteDipendente();
    }
    else if (this.function == 'deleteDocumento') {
      this.deleteDocumento();
    }
    else if (this.function == 'deleteAzienda') {
      this.deleteAzienda();
    }


    this.activeModal.close();
  }

  private deleteImgDipendente() {
    if (this.dipendente != undefined ) {
      this.dipendentiService.deleteImgDipendente(this.dipendente.id).subscribe(
        data => {
          this.alert.setMessage('Immagine rimossa con successo')
          this.alert.setSuccessAlert();
          this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate(['/dipendenti/' + this.dipendente?.id]);
          });
        },
        error => {
          this.alert.setMessage('Errore durante la rimozione dell\'immagine')
          this.alert.setDangerAlert();
        }
      );
    }
  }

  private deleteDipendente() {
    if (this.dipendente != undefined ) {
      this.dipendentiService.deleteDipendente(this.dipendente.id).subscribe(
        data => {
          this.alert.setMessage('Dipendente ' + this.dipendente?.cf +  ' rimosso con successo')
          this.alert.setSuccessAlert();
          this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate(['/dipendenti']);
          });
        },
        error => {
          this.alert.setMessage('Errore durante la rimozione del dipendente')
          this.alert.setDangerAlert();
        }
      );
    }
  }

  private deleteDocumento() {
    if (this.documento != undefined ) {
      this.documentiService.deleteDocumento(this.documento.id).subscribe(
        data => {
          this.alert.setDocumentAlert('Documento ' + this.documento?.nome +  ' rimosso con successo', 'success')
          this.refreshData.emit();
          /*
          
          this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate(['/documenti']);
          });
          */
          
        },
        error => {
          this.alert.setDocumentAlert('Errore durante la rimozione del documento', 'danger')
        }
      );
    }
  }

  private deleteAzienda() {
    if (this.azienda != undefined ) {
      this.aziendeService.deleteAzienda(this.azienda.piva).subscribe(
        data => {
          console.log("Azienda rimossa con successo");
          this.alert.setMessage('Azienda ' + this.azienda?.ragioneSociale +  ' rimossa con successo')
          this.alert.setSuccessAlert();
          this.refreshData.emit();
        },
        error => {
          console.log("Errore durante la rimozione dell'azienda");
          this.alert.setMessage('Errore durante la rimozione dell\'azienda')
          this.alert.setDangerAlert();
          this.refreshData.emit();
        }
      );
    }
  }
}