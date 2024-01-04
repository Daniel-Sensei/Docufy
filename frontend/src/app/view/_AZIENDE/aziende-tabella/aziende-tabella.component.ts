import { AfterViewInit, Component, ViewChild, Input, ChangeDetectorRef } from '@angular/core';

import { Azienda } from '../../../model/Azienda';

import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

@Component({
  selector: 'app-aziende-tabella',
  templateUrl: './aziende-tabella.component.html',
  styleUrl: './aziende-tabella.component.css'
})
export class AziendeTabellaComponent {
  displayedColumns: string[] = ['ragioneSociale', 'indirizzo', 'telefono', 'azioni'];
  dataSource: MatTableDataSource<Azienda>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  @Input() aziende: Azienda[] = [];

  constructor(private cdr: ChangeDetectorRef) {
    this.dataSource = new MatTableDataSource(this.aziende);
  }

  ngAfterViewInit() {
    // If necessario per aspettare che il componente sia caricato
    if (this.aziende.length > 0) {
      this.dataSource.data = this.aziende;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    }
    this.cdr.detectChanges();
  }


}
