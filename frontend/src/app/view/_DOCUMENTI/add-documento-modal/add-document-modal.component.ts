import { Component } from '@angular/core';
import { NgbActiveModal, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlertService } from '../../../service/alert/alert.service';
import { FormCheck } from '../../../FormCheck';
import { Documento } from '../../../model/Documento';
import { DocumentiService } from '../../../service/documenti/documenti.service';

@Component({
  selector: 'app-add-document-modal',
  templateUrl: './add-document-modal.component.html',
  styleUrls: ['./add-document-modal.component.css']
})
export class AddDocumentModalComponent {

  addDocumentForm: FormGroup;
  model: NgbDateStruct | undefined;

  private file: File | undefined;
  private dataRilascio: string = '';
  private dataScadenza: string = '';

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private alert: AlertService,
    private documentiService: DocumentiService
    ) {
    this.addDocumentForm = this.fb.group({
      tipoDocumento: ['', Validators.required],
      tipoDocumentoAltro: [''],
      dataRilascio: ['', Validators.required],
      dataScadenza: ['', Validators.required],
      file: ['', Validators.required],
    }, { validators: this.customValidation });
  }

  onFileSelected(event: any) {
    this.file = event.target.files[0];
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

      if(dataRilascio && !FormCheck.compareFutureDate(dataRilascio)){
        dataRilascioControl.setErrors({ 'futureDate': true });
      } else {
        if (dataRilascioControl.hasError('futureData')) {
          dataRilascioControl.setErrors(null);
        }
      }

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
    this.activeModal.close('Save click');

    const documentoData = this.formatData();
    //rimuovi da documentData i campi non necessari (tipoDocumento)
    console.log(documentoData);
    delete documentoData.tipoDocumento;
    delete documentoData.tipoDocumentoAltro;
    console.log(documentoData);
    this.documentiService.addDocumento(documentoData, this.file).subscribe(
      response => {
        console.log("AGGIUNTO DOCUMENTO");
        this.alert.setSuccessAlert();
      },
      error => {
        console.log("ERRORE AGGIUNTA DOCUMENTO");
        this.alert.setDangerAlert();
      }
    );
  }

  formatData(){
    const documentoData = this.addDocumentForm.value;
    if (this.dataRilascio == '' || this.dataScadenza == '') {
      this.dataRilascio = FormCheck.NgbDateToDateString(documentoData.dataRilascio);
      this.dataScadenza = FormCheck.NgbDateToDateString(documentoData.dataScadenza);
    }
    documentoData.dataRilascio = this.dataRilascio;
    documentoData.dataScadenza = this.dataScadenza;
    documentoData.nome = documentoData.tipoDocumento;
    if (documentoData.tipoDocumento == 'altro') {
      documentoData.nome = documentoData.tipoDocumentoAltro;
    }
    documentoData.file = documentoData.file.name;
    documentoData.formato = this.getFileExtension()?.toUpperCase();

    return documentoData;
  }

  getFileExtension() {
    return this.file?.name.split('.').pop();
  }
}
