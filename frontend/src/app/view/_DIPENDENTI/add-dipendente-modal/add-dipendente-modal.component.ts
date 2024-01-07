import { Component } from '@angular/core';
import { NgbActiveModal, NgbDate, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlertService } from '../../../service/alert/alert.service';

import { FormCheck } from '../../../FormCheck';
import { DipendentiService } from '../../../service/dipendenti/dipendenti.service';
import { Output, EventEmitter } from '@angular/core';


@Component({
  selector: 'app-add-dipendente-modal',
  templateUrl: './add-dipendente-modal.component.html',
  styleUrl: './add-dipendente-modal.component.css'
})
export class AddDipendenteModalComponent {
  @Output() refreshData: EventEmitter<void> = new EventEmitter<void>();

  private dataNascita: string = '';
  private dataAssunzione: string = '';

  addDipendenteForm: FormGroup;
  model: NgbDateStruct | undefined;

  public minDate: NgbDate;

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private alert: AlertService,
    private dipendentiService: DipendentiService
  ) {
    this.addDipendenteForm = this.fb.group({
      nome: ['', Validators.required],
      cognome: ['', Validators.required],
      dataNascita: ['', Validators.required],
      dataAssunzione: ['', Validators.required],
      cf: ['', Validators.required],
      ruolo: ['', Validators.required],
      email: ['', Validators.required],
      residenza: ['',],
      telefono: ['',],
      img: ['',],
    }, { validators: this.customValidation });

    this.minDate = new NgbDate(1900, 1, 1);
  }

  customValidation(group: FormGroup) {
    const nomeControl = group.get('nome');
    const cognomeControl = group.get('cognome');
    const dataNascitaControl = group.get('dataNascita');
    const dataAssunzioneControl = group.get('dataAssunzione');
    const cfControl = group.get('cf');
    const ruoloControl = group.get('ruolo');
    const emailControl = group.get('email');
    const telefonoControl = group.get('telefono');

    if (nomeControl && cognomeControl && dataNascitaControl && dataAssunzioneControl && cfControl && ruoloControl && emailControl && telefonoControl) {
      const nome = nomeControl.value;
      const cognome = cognomeControl.value;
      const dataNascita = dataNascitaControl.value;
      const dataAssunzione = dataAssunzioneControl.value;
      const cf = cfControl.value;
      const ruolo = ruoloControl.value;
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

      // Data di assunzione
      var dataAssunzioneMaggiorenne = new NgbDate(dataNascita.year + 18, dataNascita.month, dataNascita.day);
      if (dataAssunzione && FormCheck.compareTwoDates(dataAssunzioneMaggiorenne, dataAssunzione)) {
        dataAssunzioneControl.setErrors({ 'invalidDate': true });
      }
      else {
        if (dataAssunzioneControl.hasError('invalidDate')) {
          dataAssunzioneControl.setErrors(null);
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

      // Ruolo
      if (ruolo && !FormCheck.checkNome(ruolo)) {
        ruoloControl.setErrors({ 'invalidRole': true });
      } else {
        if (ruoloControl.hasError('invalidRole')) {
          ruoloControl.setErrors(null);
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

    const dipendenteData = this.addDipendenteForm.value;

    if (this.dataNascita == '' || this.dataAssunzione == '') {
      this.dataNascita = this.NgbDateToDateString(dipendenteData.dataNascita);
      this.dataAssunzione = this.NgbDateToDateString(dipendenteData.dataAssunzione);
    }
    dipendenteData.dataNascita = this.dataNascita;
    dipendenteData.dataAssunzione = this.dataAssunzione;

    const imgFile: File = this.addDipendenteForm.get('img')?.value;

    this.dipendentiService.addDipendente(dipendenteData, imgFile).subscribe(
      response => {
        // Imposta la variabile per mostrare l'alert di successo
        this.alert.setMessage(dipendenteData.cf);
        this.alert.setSuccessAlert();
        this.refreshData.emit();
      },
      error => {
        // Gestisci eventuali errori
        console.error(error);
      }
    );
  }

  NgbDateToDateString(ngbDate: NgbDate): string {
    // Converte un oggetto NgbDate in una stringa nel formato 'yyyy-MM-dd'
    // Aggiungi uno zero davanti al mese e al giorno se sono composti da un solo carattere
    let month = ngbDate.month.toString();
    let day = ngbDate.day.toString();
    if (ngbDate.month < 10) {
      month = '0' + month;
    }
    if (ngbDate.day < 10) {
      day = '0' + day;
    }
    return ngbDate.year + '-' + month + '-' + day;
  }
}