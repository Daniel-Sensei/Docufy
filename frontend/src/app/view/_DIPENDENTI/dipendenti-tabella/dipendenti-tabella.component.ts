import { AfterViewInit, Component, ViewChild, Input, ChangeDetectorRef } from '@angular/core';

import { Dipendente } from '../../../model/Dipendente';

import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { DomSanitizer } from '@angular/platform-browser';


@Component({
  selector: 'app-dipendenti-tabella',
  templateUrl: './dipendenti-tabella.component.html',
  styleUrl: './dipendenti-tabella.component.css'
})
export class DipendentiTabellaComponent implements AfterViewInit{
  displayedColumns: string[] = ['cognome', 'ruolo', 'dataNascita', 'azioni'];
  dataSource: MatTableDataSource<Dipendente>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  @Input() dipendenti: Dipendente[] = [];

  constructor(
    private cdr: ChangeDetectorRef,
    private sanitizer: DomSanitizer) {
    this.dataSource = new MatTableDataSource(this.dipendenti);
  }

  ngAfterViewInit() {
    // If necessario per aspettare che il componente sia caricato
    if (this.dipendenti.length > 0) {
      // Rendi sicuro l'URL "img" per ogni dipendente
      this.dipendenti.forEach(dipendente => {
        if (dipendente.img != '') dipendente.img = this.sanitizer.bypassSecurityTrustResourceUrl(dipendente.img) as string;
      });
      this.dataSource.data = this.dipendenti;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    }
    this.cdr.detectChanges();
  }
}

