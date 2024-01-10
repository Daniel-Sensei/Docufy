import { Component } from '@angular/core';
import { Azienda } from '../../../model/Azienda';
import { AziendeService } from '../../../service/aziende/aziende.service';
import { AlertService } from '../../../service/alert/alert.service';

import { Output, EventEmitter } from '@angular/core'; //modifica
import { FormGroup, FormBuilder, Validators } from '@angular/forms'; 

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  @Output() refreshData: EventEmitter<void> = new EventEmitter<void>();

  
  private file: File | undefined; //modifica
  modificaProfiloForm: FormGroup; //modifica
  

  azienda!: Azienda;
  datiOriginali!: Azienda; //modifica
  modificato = false;       //modifica


  constructor(
    private aziendeService: AziendeService,
    private alert: AlertService,    //modifica
    private fb: FormBuilder, //modifica
  ) {
    this.modificaProfiloForm = this.fb.group({
      ragione_sociale: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefono: ['', Validators.required],
      indirizzo: ['',Validators.required],
      img: ['']
    }, //{ validators: this.customValidation }
    );
  }

  ngOnInit(): void {
    this.getAzienda();
    
  }

  getAzienda() {
    //Stampa i dati dell'azienda
    this.aziendeService.getProfilo().subscribe( azienda => { this.azienda = azienda; this.datiOriginali={...azienda}; console.log(this.azienda)}) //modifica: ; this.datiOriginali={...azienda}
  }

  controllaModifiche(){
    this.modificato = (JSON.stringify(this.azienda) != JSON.stringify(this.datiOriginali)) || this.azienda.img!=""; //modifica
  }

  salvaModifiche(){
    this.aziendeService.updateProfilo(this.azienda).subscribe();
    this.datiOriginali={...this.azienda}; //modifica
    console.log(this.modificaProfiloForm.value);
    this.modificato=false;
   
  }

  private updateProfilo(aziendaData: any) {
    aziendaData.id = this.azienda?.piva;
    this.aziendeService.updateProfilo (aziendaData).subscribe( 
      response => {
        // Imposta la variabile per mostrare l'alert di successo
        this.alert.setMessage("Dipendente modificato con successo");
        this.alert.setSuccessAlert();
        this.refreshData.emit();
      },
      error => {
        // Gestisci eventuali errori
        this.alert.setMessage("Errore durante la modifica del dipendente");
        this.alert.setDangerAlert();
        this.refreshData.emit();
      });
  }
}
