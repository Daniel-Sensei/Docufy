import { Component } from '@angular/core';
import { MatSort, Sort } from '@angular/material/sort';
import { ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';


@Component({
  selector: 'app-dipendenti',
  templateUrl: './dipendenti.component.html',
  styleUrl: './dipendenti.component.css'
})
export class DipendentiComponent {
  @ViewChild(MatSort, { static: true }) sort!: MatSort;

  dataSource = new MatTableDataSource<any>(/* Inizializza con i dati appropriati */);


  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  onSortChange(event: Sort) {
    // Aggiorna i dati della tabella in base alle nuove informazioni di ordinamento
  }

}
