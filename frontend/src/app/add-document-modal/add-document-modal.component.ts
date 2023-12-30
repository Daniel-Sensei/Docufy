import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-document-modal',
  templateUrl: './add-document-modal.component.html',
  styleUrls: ['./add-document-modal.component.css']
})
export class AddDocumentModalComponent {

  addDocumentForm: FormGroup;

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder
  ) {
    this.addDocumentForm = this.fb.group({
      tipoDocumento: ['', Validators.required],
      tipoDocumentoAltro: ['']
    }, { validators: this.checkAltroNome });
  }

  checkAltroNome(group: FormGroup) {
    const tipoDocumentoControl = group.get('tipoDocumento');
    const tipoDocumentoAltroControl = group.get('tipoDocumentoAltro');

    if (tipoDocumentoControl && tipoDocumentoControl.value === 'altro' && tipoDocumentoAltroControl && !tipoDocumentoAltroControl.value) {
      tipoDocumentoAltroControl.setErrors({ required: true });
    } else {
      tipoDocumentoAltroControl?.setErrors(null);
    }

    return null;
  }

  submitForm() {
    // Puoi anche aggiungere qui la logica per chiudere la finestra modale, se necessario
    this.activeModal.close('Save click');
  }
}
