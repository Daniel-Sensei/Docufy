import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddDocumentModalComponent } from '../add-documento-modal/add-document-modal.component';
import { AlertService } from '../alert.service';

import { Documento } from '../model/Documento';
import { DocumentiService } from '../service/documenti.service';


@Component({
  selector: 'app-documenti',
  templateUrl: './documenti.component.html',
  styleUrl: './documenti.component.css'
})
export class DocumentiComponent {
  documenti: Documento[] = [];

  constructor(
    private modalService: NgbModal,
    public alert: AlertService,
    private documentiService: DocumentiService) { }

  ngOnInit(): void {
    this.documentiService.getAllDocumenti().subscribe(documenti => { this.documenti = documenti; });
  }

  openAddDocumentModal() {
    const modalRef = this.modalService.open(AddDocumentModalComponent, {
      size: 'md' // 'lg' sta per grande, puoi utilizzare anche 'sm' per piccolo
    });

    // Puoi gestire eventi o dati passati al modale qui
  }

}
