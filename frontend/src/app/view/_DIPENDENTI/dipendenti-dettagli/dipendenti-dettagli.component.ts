import { Component } from '@angular/core';
import { Dipendente } from '../../../model/Dipendente';

import { Router, ActivatedRoute } from '@angular/router';
import { DipendentiService } from '../../../service/dipendenti/dipendenti.service';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddDipendenteModalComponent } from '../add-dipendente-modal/add-dipendente-modal.component';
import { FileService } from '../../../service/file/file.service';

@Component({
  selector: 'app-dipendenti-dettagli',
  templateUrl: './dipendenti-dettagli.component.html',
  styleUrl: './dipendenti-dettagli.component.css'
})
export class DipendentiDettagliComponent {

  dipendente!: Dipendente;

  constructor(
    private route: ActivatedRoute,
    private dipendentiService: DipendentiService,
    private router: Router,
    private modalService: NgbModal,
    private fileService: FileService
  ) { }

  ngOnInit(): void {
    this.getDipendente();
  }

  getDipendente(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.dipendentiService.getDipendente(id).subscribe(dipendente => {
      this.dipendente = dipendente;
      if (this.dipendente == undefined) {
        this.router.navigate(['/404']);
      }
      if (this.dipendente.img != ''){
        this.fileService.getFile(this.dipendente.img).subscribe(img => {
          let objectURL = URL.createObjectURL(img);
          this.dipendente.img = objectURL;
        });
      }
    });
  }

  openUpdateDipendente(){
    const modalRef = this.modalService.open(AddDipendenteModalComponent, {
      size: 'md' // 'lg' sta per grande, puoi utilizzare anche 'sm' per piccolo
    });

    // Passa il this.dipendente al modal
    modalRef.componentInstance.dipendente = this.dipendente;
  }
}
