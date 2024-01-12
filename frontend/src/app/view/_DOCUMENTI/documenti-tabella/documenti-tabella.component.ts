import { AfterViewInit, Component, ViewChild, Input, ChangeDetectorRef, Output } from '@angular/core';

import { Documento } from '../../../model/Documento';

import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { AuthService } from '../../../service/auth/auth.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddDocumentModalComponent } from '../add-documento-modal/add-document-modal.component';
import { ConfirmModalComponent } from '../../_STATIC/confirm-modal/confirm-modal.component';
import { FileService } from '../../../service/file/file.service';
import { ActivatedRoute } from '@angular/router';
import { Dipendente } from '../../../model/Dipendente';
import { EventEmitter } from '@angular/core';


@Component({
  selector: 'app-documenti-tabella',
  templateUrl: './documenti-tabella.component.html',
  styleUrl: './documenti-tabella.component.css'
})
export class DocumentiTabellaComponent implements AfterViewInit {
  displayedColumns: string[] = ['nome', 'dataScadenza', 'stato', 'formato', 'azioni'];
  dataSource: MatTableDataSource<Documento>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  @Input() documenti: Documento[] = [];
  @Input() dipendente?: Dipendente | undefined;

  @Output() refreshData: EventEmitter<void> = new EventEmitter<void>();

  constructor(
    private cdr: ChangeDetectorRef,
    public auth: AuthService,
    private modalService: NgbModal,
    private fileService: FileService) {
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

  openUpdateDocumento(documento: Documento) {
    const modalRef = this.modalService.open(AddDocumentModalComponent, {
      size: 'md' // 'lg' sta per grande, puoi utilizzare anche 'sm' per piccolo
    });

    // Passa il this.documento al modal
    modalRef.componentInstance.documento = documento;
    if(this.dipendente != undefined) {
      modalRef.componentInstance.dipendente = this.dipendente;
    }

    modalRef.componentInstance.refreshData.subscribe(() => {
      this.refreshData.emit();
    });
  }

  openDeleteDocumento(documento: Documento) {
    const modalRef = this.modalService.open(ConfirmModalComponent, {
      size: 'md' // 'lg' sta per grande, puoi utilizzare anche 'sm' per piccolo
    });

    // Passa il this.dipendente al modal
    modalRef.componentInstance.documento = documento;
    modalRef.componentInstance.function = 'deleteDocumento';

    modalRef.componentInstance.refreshData.subscribe(() => {
      this.refreshData.emit();
    });
  }

  downloadDocumento(documento: Documento) {
    this.fileService.getFile(documento.file).subscribe(file => {
      // Get the file extension
      const extension = documento.file.split('.').pop() as string;
      
      // Set the appropriate file type based on the extension
      const fileType = this.getFileTypeByExtension(extension);
      
      // Download the file
      const blob = new Blob([file], { type: fileType });
      const url = window.URL.createObjectURL(blob);
      
      // Create a link element and trigger the download
      const a = document.createElement('a');
      a.href = url;
      a.download = `${documento.nome}.${extension}`;
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
    });
  }
  
  getFileTypeByExtension(extension: string): string {
    // Map common file extensions to MIME types
    const mimeTypes: { [key: string]: string } = {
      'pdf': 'application/pdf',
      'doc': 'application/msword',
      'docx': 'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
      // Add more mappings as needed
    };
  
    // Default to generic binary data if the extension is not in the mapping
    return mimeTypes[extension] || 'application/octet-stream';
  }
  
}
