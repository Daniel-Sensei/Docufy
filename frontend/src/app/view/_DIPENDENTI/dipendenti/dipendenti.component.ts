import { Component } from '@angular/core';
import { AlertService } from '../../../service/alert/alert.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddDipendenteModalComponent } from '../add-dipendente-modal/add-dipendente-modal.component';

import { Dipendente } from '../../../model/Dipendente';
import { DipendentiService } from '../../../service/dipendenti/dipendenti.service';
import { FileService } from '../../../service/file/file.service';

@Component({
  selector: 'app-dipendenti',
  templateUrl: './dipendenti.component.html',
  styleUrl: './dipendenti.component.css'
})
export class DipendentiComponent {
  dipendenti: Dipendente[] = [];

  constructor(
    private modalService: NgbModal,
    public alert: AlertService,
    private dipendentiService: DipendentiService,
    private fileService: FileService
  ) { }

  ngOnInit(): void {
    this.dipendentiService.getMyDipendenti().subscribe(dipendenti => {
      this.dipendenti = dipendenti;

      this.dipendenti.forEach(dipendente => {
        if (dipendente.img != '') {
          this.fileService.getFile(dipendente.img).subscribe(img => {
            let objectURL = URL.createObjectURL(img);
            dipendente.img = objectURL;
          });
        }
      });

    });
  }

  openAddDocumentModal() {
    const modalRef = this.modalService.open(AddDipendenteModalComponent, {
      size: 'md' // 'lg' sta per grande, puoi utilizzare anche 'sm' per piccolo
    });

    // Puoi gestire eventi o dati passati al modale qui
    // Ascolta l'evento emesso dal componente figlio
    modalRef.componentInstance.refreshData.subscribe(() => {
      // Aggiorna i dati richiamando nuovamente ngOnInit
      this.dipendenti = [];
      this.ngOnInit();
    });
  }

}
