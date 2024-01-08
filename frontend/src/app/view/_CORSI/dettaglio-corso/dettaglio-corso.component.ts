import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertService } from '../../../service/alert/alert.service';
import { AddDipendenteCorsoModalComponent } from '../add-dipendente-corso-modal/add-dipendente-corso-modal.component';

import { Dipendente } from '../../../model/Dipendente';
import { DipendentiService } from '../../../service/dipendenti/dipendenti.service';

@Component({
  selector: 'app-dettaglio-corso',
  templateUrl: './dettaglio-corso.component.html',
  styleUrl: './dettaglio-corso.component.css'
})

export class DettaglioCorsoComponent {
  dipendentiIscritti: Dipendente[] = [];

  constructor(
    private modalService: NgbModal,
    public alert: AlertService,
    private dipendentiService: DipendentiService
    ) { }

  openAddDipendenteCorso(){
    const modalRef = this.modalService.open(AddDipendenteCorsoModalComponent, {
      size: 'md' // 'lg' sta per grande, puoi utilizzare anche 'sm' per piccolo
    });
  
  }

  ngOnInit(): void {
    //this.dipendentiService.getMyDipendenti().subscribe(dipendenti => { this.dipendentiIscritti = dipendenti; });
  }

}
