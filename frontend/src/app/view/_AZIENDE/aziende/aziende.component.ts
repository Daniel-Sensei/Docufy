import { Component } from '@angular/core';
import { AlertService } from '../../../alert.service';

import { AziendeService } from '../../../service/aziende.service';
import { Azienda } from '../../../model/Azienda';
import { AddAziendaModalComponent } from '../add-azienda-modal/add-azienda-modal.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-aziende',
  templateUrl: './aziende.component.html',
  styleUrl: './aziende.component.css'
})
export class AziendeComponent {

  constructor(
    public alert: AlertService,
    private aziendeService: AziendeService,
    private modalService: NgbModal
  ) { }

  aziende: Azienda[] = [];

  ngOnInit(): void {
    this.aziendeService.getAllAziende().subscribe(aziende => { this.aziende = aziende; });
  }

  openAddAzienda(){
    const modalRef = this.modalService.open(AddAziendaModalComponent, {
      size: 'md' 
  });

}

}
