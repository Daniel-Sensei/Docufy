import { Component } from '@angular/core';
import { NgbActiveModal, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlertService } from '../../../alert.service';

@Component({
  selector: 'app-add-azienda-modal',
  templateUrl: './add-azienda-modal.component.html',
  styleUrl: './add-azienda-modal.component.css'
})
export class AddAziendaModalComponent {

  addAziendaForm: FormGroup;
  model: NgbDateStruct | undefined;

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private alert: AlertService,
    ) {
    this.addAziendaForm = this.fb.group({
      ragione_sociale: ['', Validators.required],
      partita_iva: ['', Validators.required],
      email : ['', Validators.required],
      telefono: ['', Validators.required],
      indirizzo: ['',Validators.required],
      img: ['', ],
    }, { validators: this.customValidation });
  }
  
  customValidation(group: FormGroup) {
    const ragione_socialeControllo = group.get('Ragione_sociale');
    const partita_ivaControllo = group.get('partita_iva');
    const emailControllo = group.get('email');
    const telefonoControllo = group.get('telefono');
    const indirizzoControllo = group.get('indirizzo');
  
    if (ragione_socialeControllo?.value && partita_ivaControllo?.value && emailControllo?.value && telefonoControllo?.value && indirizzoControllo?.value) {

      const partita_iva = partita_ivaControllo.value;
      const indirizzo = indirizzoControllo.value;
      const email = emailControllo.value;


      if (partita_ivaControllo && partita_iva.length !== 11) {
        partita_ivaControllo.setErrors({ 'invalidPIvaLength': true });
      } else {
        //Rimuovi l'errore se la lunghezza del codice fiscale è corretta
        if (partita_iva.hasError('invalidCFLength')) {
          partita_iva.setErrors(null);
        }
      }
  
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  
      if (email && !emailRegex.test(email)) {
        emailControllo.setErrors({ 'invalidEmail': true });
      } else {
        // Rimuovi l'errore se l'email è valida
        if (emailControllo.hasError('invalidEmail')) {
          emailControllo.setErrors(null);
        }
      }
    }
  }

















  submitForm() { 
    if (this.addAziendaForm.valid) {
      (this.alert as any).success('Azienda aggiunta con successo');
      this.activeModal.close(this.addAziendaForm.value);
    } else {
      (this.alert as any).error('Errore durante l\'aggiunta dell\'azienda');
    }
  }

}
