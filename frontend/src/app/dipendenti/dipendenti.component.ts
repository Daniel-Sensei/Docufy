import { Component } from '@angular/core';
import { MatSort, Sort } from '@angular/material/sort';
import { ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { AlertService } from '../alert.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddDipendenteModalComponent } from '../add-dipendente-modal/add-dipendente-modal.component';

@Component({
  selector: 'app-dipendenti',
  templateUrl: './dipendenti.component.html',
  styleUrl: './dipendenti.component.css'
})
export class DipendentiComponent {
  @ViewChild(MatSort, { static: true }) sort!: MatSort;

  constructor(
    private modalService: NgbModal,
    public alert: AlertService
    ) { }

  openAddDocumentModal() {
    const modalRef = this.modalService.open(AddDipendenteModalComponent, {
      size: 'md' // 'lg' sta per grande, puoi utilizzare anche 'sm' per piccolo
    });
  
    // Puoi gestire eventi o dati passati al modale qui
  }

  dataSource = new MatTableDataSource<any>(/* Inizializza con i dati appropriati */);


  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  onSortChange(event: Sort) {
    // Aggiorna i dati della tabella in base alle nuove informazioni di ordinamento
  }

}
