import { AfterViewInit, Component, ViewChild, Input, ChangeDetectorRef } from '@angular/core';

import { Documento } from '../../../model/Documento';

import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { AuthService } from '../../../service/auth/auth.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddDocumentModalComponent } from '../add-documento-modal/add-document-modal.component';


@Component({
  selector: 'app-documenti-tabella',
  templateUrl: './documenti-tabella.component.html',
  styleUrl: './documenti-tabella.component.css'
})
export class DocumentiTabellaComponent implements AfterViewInit{
  displayedColumns: string[] = ['nome', 'dataScadenza', 'stato', 'formato', 'azioni'];
  dataSource: MatTableDataSource<Documento>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  
  @Input() documenti: Documento[] = [];

  constructor(
    private cdr: ChangeDetectorRef,
    public auth: AuthService,
    private modalService: NgbModal) {
    this.dataSource = new MatTableDataSource(this.documenti);
  }

  ngAfterViewInit() {
    // If necessario per aspettare che il componente sia caricato
    if (this.documenti.length > 0) {
      this.dataSource.data = this.documenti;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    }

    this.cdr.detectChanges();
  }

  openUpdateDipendente() {
    const modalRef = this.modalService.open(AddDocumentModalComponent, {
      size: 'md' // 'lg' sta per grande, puoi utilizzare anche 'sm' per piccolo
    });

    // Passa il this.dipendente al modal
    modalRef.componentInstance.documento = this.documenti[0];

    /*
    modalRef.componentInstance.refreshData.subscribe(() => {
      // Aggiorna i dati richiamando nuovamente ngOnInit
      this.ngOnInit();
    });
    */
  }
}
