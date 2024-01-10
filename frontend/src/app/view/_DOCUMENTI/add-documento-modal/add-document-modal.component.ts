import { Component, Input } from '@angular/core';
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
  @Input() documento: Documento | undefined;

  tipiDocumentoDipendente = ['Patente', 'Carta d\'identità', 'Passaporto', 'Codice fiscale', 'Carta di circolazione', 'Altro'];
  tipiDocumentoAzienda = ['Certificato di iscrizione alla CCIAA', 'Certificato di iscrizione all\'INPS', 'Certificato di iscrizione all\'INAIL', 'Visura Camerale', 'Altro'];

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

  ngOnInit(): void {
    if (this.documento) {
      this.initializeFormWithDocumento(this.documento);
    }
  }

  private initializeFormWithDocumento(documento: Documento): void {
    const dataRilascioArray = documento.dataRilascio.split('-');
    const dataScadenzaArray = documento.dataScadenza.split('-');

    this.addDocumentForm.patchValue({
      // controlla che documento.tipoDocumento sia tra i tipiDocumentiAzienda
      tipoDocumento: this.tipiDocumentoAzienda.includes(documento.nome) ? documento.nome : 'Altro',
      tipoDocumentoAltro: this.tipiDocumentoAzienda.includes(documento.nome) ? '' : documento.nome,

      dataRilascio: {
        year: +dataRilascioArray[0],
        month: +dataRilascioArray[1],
        day: +dataRilascioArray[2]
      },
      dataScadenza: {
        year: +dataScadenzaArray[0],
        month: +dataScadenzaArray[1],
        day: +dataScadenzaArray[2]
      },

    });
  }


  onFileSelected(event: any) {
    this.file = event.target.files[0];
  }

  customValidation(group: FormGroup) {
    const tipoDocumentoControl = group.get('tipoDocumento');
    const tipoDocumentoAltroControl = group.get('tipoDocumentoAltro');

    if (tipoDocumentoControl && tipoDocumentoControl.value === 'Altro' && tipoDocumentoAltroControl && !tipoDocumentoAltroControl.value) {
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
    if(this.documento == undefined){
      this.addDocumento(documentoData);
    }
    else{
      documentoData.id = this.documento.id;
      this.updateDocumento(documentoData);
    }
  }

  private addDocumento(documentoData: any) {
    console.log("AGGIUNGI DOCUMENTO");
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

  private updateDocumento(documentoData: any) {
    console.log("MODIFICA DOCUMENTO");
    this.documentiService.updateDocumento(documentoData, this.file).subscribe(
      response => {
        console.log("MODIFICATO DOCUMENTO");
        this.alert.setSuccessAlert();
      },
      error => {
        console.log("ERRORE MODIFICA DOCUMENTO");
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
    if (documentoData.tipoDocumento == 'Altro') {
      documentoData.nome = documentoData.tipoDocumentoAltro;
    }
    documentoData.file = documentoData.file.name;
    documentoData.formato = this.getFileExtension()?.toUpperCase();

    delete documentoData.tipoDocumento;
    delete documentoData.tipoDocumentoAltro;

    return documentoData;
  }

  getFileExtension() {
    return this.file?.name.split('.').pop();
  }
}
