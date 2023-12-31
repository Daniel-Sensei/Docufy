import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertService } from '../alert.service';
import { AddDipendenteCorsoModalComponent } from '../add-dipendente-corso-modal/add-dipendente-corso-modal.component';

@Component({
  selector: 'app-dettaglio-corso',
  templateUrl: './dettaglio-corso.component.html',
  styleUrl: './dettaglio-corso.component.css'
})

export class DettaglioCorsoComponent {
  constructor(
    private modalService: NgbModal,
    public alert: AlertService
    ) { }

  openAddDipendenteCorso(){
    const modalRef = this.modalService.open(AddDipendenteCorsoModalComponent, {
      size: 'md' // 'lg' sta per grande, puoi utilizzare anche 'sm' per piccolo
    });
  
  }

}
