import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertService } from '../../../service/alert/alert.service';
import { AddDipendenteCorsoModalComponent } from '../add-dipendente-corso-modal/add-dipendente-corso-modal.component';

import { Dipendente } from '../../../model/Dipendente';
import { DipendentiService } from '../../../service/dipendenti/dipendenti.service';

import { Corso } from '../../../model/Corso';
import { CorsiService } from '../../../service/corsi/corsi.service';
import { Router, ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-dettaglio-corso',
  templateUrl: './dettaglio-corso.component.html',
  styleUrl: './dettaglio-corso.component.css'
})

export class DettaglioCorsoComponent {
  corso?: Corso;
  dipendentiIscritti: Dipendente[] = [];

  constructor(
    private modalService: NgbModal,
    public alert: AlertService,
    private dipendentiService: DipendentiService,
    private route: ActivatedRoute,
    private router: Router,
    private corsiService: CorsiService
    ) { }

  openAddDipendenteCorso(){
    const modalRef = this.modalService.open(AddDipendenteCorsoModalComponent, {
      size: 'md' // 'lg' sta per grande, puoi utilizzare anche 'sm' per piccolo
    });
  }

  ngOnInit(): void {
    this.getCorso();
  }

  getCorso(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.corsiService.getCorso(id).subscribe(corso => {
      this.corso = corso;
      if (this.corso == undefined) {
        this.router.navigate(['/404']);
      }
    });
  }

}
