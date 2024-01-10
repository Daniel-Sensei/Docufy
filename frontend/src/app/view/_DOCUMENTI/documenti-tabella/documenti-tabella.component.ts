import { AfterViewInit, Component, ViewChild, Input, ChangeDetectorRef } from '@angular/core';

import { Documento } from '../../../model/Documento';

import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { AuthService } from '../../../service/auth/auth.service';

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
    public auth: AuthService) {
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
}
