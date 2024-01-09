import { Component } from '@angular/core';
import { AlertService } from '../../../service/alert/alert.service';

import { AziendeService } from '../../../service/aziende/aziende.service';
import { Azienda } from '../../../model/Azienda';
import { AddAziendaModalComponent } from '../add-azienda-modal/add-azienda-modal.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Router } from '@angular/router';

@Component({
  selector: 'app-aziende',
  templateUrl: './aziende.component.html',
  styleUrl: './aziende.component.css'
})
export class AziendeComponent {

  constructor(
    public alert: AlertService,
    private aziendeService: AziendeService,
    private modalService: NgbModal,
    private router: Router
  ) { }

  aziende: Azienda[] = [];

  ngOnInit(): void {
    this.aziendeService.getAziende().subscribe(
      aziende => { this.aziende = aziende;},
      errror => { 
        if(errror.status == 401){
          this.router.navigate(['/401']);
        }
      });
  }

  openAddAzienda(){
    const modalRef = this.modalService.open(AddAziendaModalComponent, {
      size: 'md' 
  });

}

}
