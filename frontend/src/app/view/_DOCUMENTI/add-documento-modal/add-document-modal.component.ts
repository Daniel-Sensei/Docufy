import { Component } from '@angular/core';
import { NgbActiveModal, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlertService } from '../../../service/alert/alert.service';
import { FormCheck } from '../../../FormCheck';


@Component({
  selector: 'app-add-document-modal',
  templateUrl: './add-document-modal.component.html',
  styleUrls: ['./add-document-modal.component.css']
})
export class AddDocumentModalComponent {

  addDocumentForm: FormGroup;
  model: NgbDateStruct | undefined;

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private alert: AlertService,
    ) {
    this.addDocumentForm = this.fb.group({
      tipoDocumento: ['', Validators.required],
      tipoDocumentoAltro: [''],
      dataRilascio: ['', Validators.required],
      dataScadenza: ['', Validators.required],
      file: ['', Validators.required],
    }, { validators: this.customValidation });
  }

  customValidation(group: FormGroup) {
    const tipoDocumentoControl = group.get('tipoDocumento');
    const tipoDocumentoAltroControl = group.get('tipoDocumentoAltro');

    if (tipoDocumentoControl && tipoDocumentoControl.value === 'altro' && tipoDocumentoAltroControl && !tipoDocumentoAltroControl.value) {
      tipoDocumentoAltroControl.setErrors({ required: true });
    } else {
      tipoDocumentoAltroControl?.setErrors(null);
    }

    const dataRilascioControl = group.get('dataRilascio');
    const dataScadenzaControl = group.get('dataScadenza');

    if (dataRilascioControl && dataScadenzaControl) {
      const dataRilascio = dataRilascioControl.value;
      const dataScadenza = dataScadenzaControl.value;

      if (dataRilascio && dataScadenza && FormCheck.compareTwoDates(dataRilascio, dataScadenza)) {
        dataScadenzaControl.setErrors({ 'invalidDate': true });
      } else {
        if (dataScadenzaControl.hasError('invalidDate')) {
          dataScadenzaControl.setErrors(null);
        }
      }
    }

  }

  submitForm() {
    // Puoi anche aggiungere qui la logica per chiudere la finestra modale, se necessario
    this.activeModal.close('Save click');

    //stampa a console i valori del form
    console.log(this.addDocumentForm.value);
  
    // Imposta la variabile per mostrare l'alert di successo
    this.alert.setSuccessAlert();
  }
}
