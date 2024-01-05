import { Component } from '@angular/core';

import { NgbActiveModal, NgbDate, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlertService } from '../../../service/alert/alert.service';
import { FormCheck } from '../../../FormCheck';
import { AbstractControl } from '@angular/forms';

@Component({
  selector: 'app-add-corso-modal',
  templateUrl: './add-corso-modal.component.html',
  styleUrl: './add-corso-modal.component.css'
})
export class AddCorsoModalComponent {

  addCorsoForm: FormGroup;
  model: NgbDateStruct | undefined;

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private alert: AlertService,
  ) {
    this.addCorsoForm = this.fb.group({
      nome_corso: ['', [Validators.required, Validators.minLength(1),]],
      categoria: ['', [Validators.required, Validators.minLength(1),]],
      prezzo: ['', [Validators.required, Validators.minLength(1)]],
      durata: ['', [Validators.required, Validators.minLength(1)] ],
      posti: ['', [Validators.required, Validators.minLength(1)] ],
      descrizione: ['', [Validators.required, Validators.minLength(1)]],
      esame_finale: [false],
    }, 
    );
  }


  submitForm() {
    // Puoi anche aggiungere qui la logica per chiudere la finestra modale, se necessario
    this.activeModal.close('Save click');
    

    //stampa a console i valori del form
    console.log(this.addCorsoForm.value);

    // Imposta la variabile per mostrare l'alert di successo

    this.alert.setSuccessAlert();
  }
}
