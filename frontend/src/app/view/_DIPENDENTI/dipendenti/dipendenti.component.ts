import { Component } from '@angular/core';
import { AlertService } from '../../../service/alert/alert.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddDipendenteModalComponent } from '../add-dipendente-modal/add-dipendente-modal.component';

import { Dipendente } from '../../../model/Dipendente';
import { DipendentiService } from '../../../service/dipendenti/dipendenti.service';
import { FileService } from '../../../service/file/file.service';
import { forkJoin, Observable, map } from 'rxjs';
import { AuthService } from '../../../service/auth/auth.service';

@Component({
  selector: 'app-dipendenti',
  templateUrl: './dipendenti.component.html',
  styleUrl: './dipendenti.component.css'
})
export class DipendentiComponent {
  dipendenti: Dipendente[] = [];
  isInitialized: boolean = false; // Add the flag

  constructor(
    private modalService: NgbModal,
    public alert: AlertService,
    private dipendentiService: DipendentiService,
    private fileService: FileService,
    private auth: AuthService
  ) { }

  ngOnInit(): void {
    var pIva: string = '';

    if (this.auth.getRole() == 'A') {
      pIva = this.auth.getCurrentPIva()!;
    }
    else {
      pIva = this.auth.getSelectedPIva()!;
    }
    
    this.dipendentiService.getDipendenti(pIva).subscribe(
      dipendenti => {
        this.dipendenti = dipendenti;
        this.setDipendentiImages().subscribe(() => {
          this.isInitialized = true; // Set the flag to true after initialization
        });
      },
      error => {
        console.error('Errore nel recupero dei dipendenti:', error);
        // Handle the error here, e.g., show a message to the user or redirect to an error page.
      }
    );
  }

  setDipendentiImages(): Observable<void[]> {
    const observables: Observable<void>[] = [];

    this.dipendenti.forEach(dipendente => {
      if (dipendente.img !== '') {
        const observable = this.fileService.getFile(dipendente.img).pipe(
          map(img => {
            let objectURL = URL.createObjectURL(img);
            dipendente.img = objectURL;
          })
        );
        observables.push(observable);
      }
    });

    // Use forkJoin to wait for all observables to complete
    return forkJoin(observables);
  }

  openAddDocumentModal() {
    const modalRef = this.modalService.open(AddDipendenteModalComponent, {
      size: 'md' // 'lg' sta per grande, puoi utilizzare anche 'sm' per piccolo
    });

    // Puoi gestire eventi o dati passati al modale qui
    // Ascolta l'evento emesso dal componente figlio
    modalRef.componentInstance.refreshData.subscribe(() => {
      // Aggiorna i dati richiamando nuovamente ngOnInit
      this.dipendenti = [];
      this.ngOnInit();
    });
  }

}
