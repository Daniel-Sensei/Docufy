import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddDocumentModalComponent } from '../add-document-modal/add-document-modal.component';
@Component({
  selector: 'app-documenti',
  templateUrl: './documenti.component.html',
  styleUrl: './documenti.component.css'
})
export class DocumentiComponent {
  constructor(private modalService: NgbModal) {}

  openAddDocumentModal() {
    const modalRef = this.modalService.open(AddDocumentModalComponent, {
      size: 'md' // 'lg' sta per grande, puoi utilizzare anche 'sm' per piccolo
    });
  
    // Puoi gestire eventi o dati passati al modale qui
  }
  
}
