import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddDocumentModalComponent } from '../add-documento-modal/add-document-modal.component';
import { AlertService } from '../../../service/alert/alert.service';

import { Documento } from '../../../model/Documento';
import { DocumentiService } from '../../../service/documenti/documenti.service';
import { AuthService } from '../../../service/auth/auth.service';


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
    private documentiService: DocumentiService,
    public auth: AuthService) { }

  ngOnInit(): void {
    var pIva: string = '';
    if (this.auth.getRole() == 'A') {
      pIva = this.auth.getCurrentPIva()!;
    }
    else {
      pIva = this.auth.getSelectedPIva()!;
    }

    this.documentiService.getDocumentiAzienda(pIva).subscribe(documenti => { this.documenti = documenti; });
  }

  openAddDocumentModal() {
    const modalRef = this.modalService.open(AddDocumentModalComponent, {
      size: 'md' // 'lg' sta per grande, puoi utilizzare anche 'sm' per piccolo
    });

    // Volgio aggiungere un documento aziendale, quindi non passo il dipendente
    //modalRef.componentInstance.dipendente = false;

    modalRef.componentInstance.refreshData.subscribe(() => {
      // Aggiorna i dati richiamando nuovamente ngOnInit
      this.documenti = [];
      this.ngOnInit();
    });
  }

}
