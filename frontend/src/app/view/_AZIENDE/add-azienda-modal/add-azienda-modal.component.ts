import { Component } from '@angular/core';
import { NgbActiveModal, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlertService } from '../../../service/alert/alert.service';

@Component({
  selector: 'app-add-azienda-modal',
  templateUrl: './add-azienda-modal.component.html',
  styleUrl: './add-azienda-modal.component.css'
})
export class AddAziendaModalComponent {

  addAziendaForm: FormGroup;
  model: NgbDateStruct | undefined;

  constructor(
    //public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    //private alert: AlertService,
    ) {
    this.addAziendaForm = this.fb.group({
      ragione_sociale: ['', Validators.required],
      partita_iva: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefono: ['', Validators.required],
      indirizzo: ['',Validators.required]
    }, { validators: this.customValidation });
  }
  
  customValidation(group: FormGroup) {
    const partita_ivaControllo = group.get('partita_iva');

  
    if (partita_ivaControllo?.value) {
      const partita_iva = partita_ivaControllo.value;
      //Controlla la lunghezza della partita iva
      if (partita_ivaControllo && partita_iva.length !== 11) {
        partita_ivaControllo.setErrors({ 'invalidPIvaLength': true });
      } else {
        /*if (partita_iva.hasError('invalidCFLength')) {
          partita_iva.setErrors(null);
        }*/
      }

      //Controlla che la partita iva sia composta solo da numeri
      if (partita_ivaControllo && !/^[0-9]*$/.test(partita_iva)) {
        partita_ivaControllo.setErrors({ 'invalidPIva': true });
      } else {
        /*if (partita_iva.hasError('invalidPIva')) {
          partita_iva.setErrors(null);
        }*/
      }
      
      //Controlla che la partita iva sia valida

    }
  }


  submitForm() { 
    console.log(this.addAziendaForm.value);
    //this.alert.success("Azienda aggiunta con successo");
    //this.activeModal.close(this.addAziendaForm.value);
  }

}