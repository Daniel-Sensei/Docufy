import { Component } from '@angular/core';
import { NgbActiveModal, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlertService } from '../alert.service';

@Component({
  selector: 'app-add-dipendente-modal',
  templateUrl: './add-dipendente-modal.component.html',
  styleUrl: './add-dipendente-modal.component.css'
})
export class AddDipendenteModalComponent {

  addDipendenteForm: FormGroup;
  model: NgbDateStruct | undefined;

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private alert: AlertService,
    ) {
    this.addDipendenteForm = this.fb.group({
      nome: ['', Validators.required],
      cognome: ['', Validators.required],
      dataNascita: ['', Validators.required],
      cf : ['', Validators.required],
      email: ['', Validators.required],
      residenza: ['', ],
      telefono: ['', ],
    }, { validators: this.customValidation });
  }

  customValidation(group: FormGroup) {
    const dataNascitaControl = group.get('dataNascita');
    const cfControl = group.get('cf');
    const emailControl = group.get('email');
  
    if (dataNascitaControl && cfControl && emailControl) {
      const dataNascita = dataNascitaControl.value;
      const cf = cfControl.value;
      const email = emailControl.value;
  
      // Esempio di validazione: Verifica che l'utente abbia almeno 18 anni
      const today = new Date();
      const birthDate = new Date(dataNascita.year, dataNascita.month - 1, dataNascita.day);
      const age = today.getFullYear() - birthDate.getFullYear();
  
      /*
      if (age < 18) {
        dataNascitaControl.setErrors({ 'invalidAge': true });
      } else {
        // Rimuovi l'errore se la data di nascita è valida
        if (dataNascitaControl.hasError('invalidAge')) {
          dataNascitaControl.setErrors(null);
        }
      }
      */
  
      // Esempio di validazione: Verifica che il codice fiscale abbia una lunghezza corretta
      if (cf && cf.length !== 16) {
        cfControl.setErrors({ 'invalidCFLength': true });
      } else {
        // Rimuovi l'errore se la lunghezza del codice fiscale è corretta
        if (cfControl.hasError('invalidCFLength')) {
          cfControl.setErrors(null);
        }
      }
  
      // Esempio di validazione: Verifica che l'email sia valida
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  
      if (email && !emailRegex.test(email)) {
        emailControl.setErrors({ 'invalidEmail': true });
      } else {
        // Rimuovi l'errore se l'email è valida
        if (emailControl.hasError('invalidEmail')) {
          emailControl.setErrors(null);
        }
      }
    }
  }
  

  submitForm() {
    // Puoi anche aggiungere qui la logica per chiudere la finestra modale, se necessario
    this.activeModal.close('Save click');

    //stampa a console i valori del form
    console.log(this.addDipendenteForm.value);
  
    // Imposta la variabile per mostrare l'alert di successo
    this.alert.setSuccessAlert();
  }

}
