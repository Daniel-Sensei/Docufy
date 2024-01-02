import { Component } from '@angular/core';
import { Documento } from '../model/Documento';

import { DocumentiService } from '../service/documenti.service';

@Component({
  selector: 'app-documenti-tabella',
  templateUrl: './documenti-tabella.component.html',
  styleUrl: './documenti-tabella.component.css'
})
export class DocumentiTabellaComponent {

  constructor(private documentiService: DocumentiService) { }

  documenti: Documento[] = [];

  ngOnInit(): void {
    this.documentiService.getAllDocumenti().subscribe(result => this.documenti = result);
  }
}
