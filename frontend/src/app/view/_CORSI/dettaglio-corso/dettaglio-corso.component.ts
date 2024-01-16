import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertService } from '../../../service/alert/alert.service';
import { AddDipendenteCorsoModalComponent } from '../add-dipendente-corso-modal/add-dipendente-corso-modal.component';

import { Dipendente } from '../../../model/Dipendente';

import { Corso } from '../../../model/Corso';
import { CorsiService } from '../../../service/corsi/corsi.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../service/auth/auth.service';
import { AddCorsoModalComponent } from '../add-corso-modal/add-corso-modal.component';
import { ConfirmModalComponent } from '../../GENERIC/confirm-modal/confirm-modal.component';


@Component({
  selector: 'app-dettaglio-corso',
  templateUrl: './dettaglio-corso.component.html',
  styleUrl: './dettaglio-corso.component.css'
})

export class DettaglioCorsoComponent {
  corso?: Corso;
  dipendentiIscritti?: Dipendente[];

  constructor(
    private modalService: NgbModal,
    public alert: AlertService,
    private route: ActivatedRoute,
    private router: Router,
    private corsiService: CorsiService,
    public auth: AuthService
  ) { }

  ngOnInit(): void {
    this.getCorso();

    this.getDipendentiIscritti();
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

  getDipendentiIscritti(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.corsiService.getDipendentiIscritti(id).subscribe(dipendentiIscritti => {
      this.dipendentiIscritti = dipendentiIscritti;
    });
  }

  openUpdateCorsoModal() {
    const modalRef = this.modalService.open(AddCorsoModalComponent, {
      size: 'md' // 'lg' sta per grande, puoi utilizzare anche 'sm' per piccolo
    });

    modalRef.componentInstance.corso = this.corso;

    modalRef.componentInstance.refreshData.subscribe(() => {
      this.getCorso();
    });
  }

  openAddDipendentiCorso() {
    const modalRef = this.modalService.open(AddDipendenteCorsoModalComponent, {
      size: 'md' // 'lg' sta per grande, puoi utilizzare anche 'sm' per piccolo
    });

    modalRef.componentInstance.idCorso = this.corso?.id;

    modalRef.componentInstance.refreshData.subscribe(() => {
      // Aggiorna i dati richiamando nuovamente ngOnInit
      this.corso = undefined;
      this.dipendentiIscritti = [];
      this.ngOnInit();
    });
  }

  openDeleteCorso() {
    const modalRef = this.modalService.open(ConfirmModalComponent, {
      size: 'md' // 'lg' sta per grande, puoi utilizzare anche 'sm' per piccolo
    });

    // Passa il this.dipendente al modal
    modalRef.componentInstance.corso = this.corso;
    modalRef.componentInstance.function = 'deleteCorso';

    modalRef.componentInstance.refreshData.subscribe(() => {
      // Aggiorna i dati richiamando nuovamente ngOnInit
      this.router.navigate(['/corsi']);
    });
  }

}
