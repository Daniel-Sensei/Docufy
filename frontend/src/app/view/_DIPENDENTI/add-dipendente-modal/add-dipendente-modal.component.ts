import { Component } from '@angular/core';
import { NgbActiveModal, NgbDate, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlertService } from '../../../service/alert/alert.service';

import { FormCheck } from '../../../FormCheck';

@Component({
  selector: 'app-add-dipendente-modal',
  templateUrl: './add-dipendente-modal.component.html',
  styleUrl: './add-dipendente-modal.component.css'
})
export class AddDipendenteModalComponent {

  addDipendenteForm: FormGroup;
  model: NgbDateStruct | undefined;

  public minDate: NgbDate;

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private alert: AlertService,
  ) {
    this.addDipendenteForm = this.fb.group({
      nome: ['', Validators.required],
      cognome: ['', Validators.required],
      dataNascita: ['', Validators.required],
      cf: ['', Validators.required],
      email: ['', Validators.required],
      residenza: ['',],
      telefono: ['',],
    }, { validators: this.customValidation });

    this.minDate = new NgbDate(1900, 1, 1);
  }

  customValidation(group: FormGroup) {
    const nomeControl = group.get('nome');
    const cognomeControl = group.get('cognome');
    const dataNascitaControl = group.get('dataNascita');
    const cfControl = group.get('cf');
    const emailControl = group.get('email');
    const telefonoControl = group.get('telefono');

    if (nomeControl && cognomeControl && dataNascitaControl && cfControl && emailControl && telefonoControl) {
      const nome = nomeControl.value;
      const cognome = cognomeControl.value;
      const dataNascita = dataNascitaControl.value;
      const cf = cfControl.value;
      const email = emailControl.value;
      const telefono = telefonoControl?.value;

      // Nome
      if (nome && !FormCheck.checkNome(nome)) {
        nomeControl.setErrors({ 'invalidName': true });
      } else {
        if (nomeControl.hasError('invalidName')) {
          nomeControl.setErrors(null);
        }
      }

      // Cognome
      if (cognome && !FormCheck.checkNome(cognome)) {
        cognomeControl.setErrors({ 'invalidSurname': true });
      } else {
        if (cognomeControl.hasError('invalidSurname')) {
          cognomeControl.setErrors(null);
        }
      }

      // Data di nascita
      if (dataNascita && !FormCheck.dataNascitaMinima(dataNascita)) {
        dataNascitaControl.setErrors({ 'underage': true });
      } else {
        if (dataNascitaControl.hasError('underage')) {
          dataNascitaControl.setErrors(null);
        }
      }

      // CF
      if (cf && !FormCheck.checkCodiceFiscale(cf)) {
        cfControl.setErrors({ 'invalidCFLength': true });
      } else {
        if (cfControl.hasError('invalidCFLength')) {
          cfControl.setErrors(null);
        }
      }

      // Email
      if (email && !FormCheck.checkEmail(email)) {
        emailControl.setErrors({ 'invalidEmail': true });
      } else {
        if (emailControl.hasError('invalidEmail')) {
          emailControl.setErrors(null);
        }
      }

      // Telefono
      if (telefono && !FormCheck.checkTelefono(telefono)) {
        telefonoControl?.setErrors({ 'invalidPhone': true });
      } else {
        if (telefonoControl?.hasError('invalidPhone')) {
          telefonoControl?.setErrors(null);
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
    this.alert.setMessage('Dipendente 1');
    this.alert.setSuccessAlert();
  }
}