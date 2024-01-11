import { Component } from '@angular/core';
import { Azienda } from '../../../model/Azienda';
import { AziendeService } from '../../../service/aziende/aziende.service';
import { AlertService } from '../../../service/alert/alert.service';

import { Output, EventEmitter } from '@angular/core'; //modifica
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { FileService } from '../../../service/file/file.service';
import { FormCheck } from '../../../FormCheck';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  @Output() refreshData: EventEmitter<void> = new EventEmitter<void>();


  private file: File | undefined; //modifica
  modificaProfiloForm: FormGroup; //modifica
  modificaPasswordForm: FormGroup; //modifica


  azienda!: Azienda;
  datiOriginali!: Azienda; //modifica
  modificato = false;       //modifica


  constructor(
    private aziendeService: AziendeService,
    private alert: AlertService,    //modifica
    private fb: FormBuilder, //modifica
    private fileService: FileService,
  ) {
    this.modificaProfiloForm = this.fb.group({
      ragione_sociale: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefono: ['', Validators.required],
      indirizzo: ['', Validators.required],
      img: ['']
    }, { validators: this.customValidationMofidicaProfilo }
    );

    this.modificaPasswordForm = this.fb.group({
      password: ['', Validators.required],
      nuova_password: ['', Validators.required],
      conferma_password: ['', Validators.required]
    }, { validators: this.customValidationPassword }
    );
  }

  ngOnInit(): void {
    this.getAzienda();

  }

  customValidationMofidicaProfilo(group: FormGroup) {
    const ragione_socialeControl = group.get('ragione_sociale');
    const emailControl = group.get('email');
    const telefonoControl = group.get('telefono');

    const nuova_passwordControl = group.get('nuova_password');
    const conferma_passwordfControl = group.get('conferma_password');
    const PasswordControl = group.get('password');
    
    

    if (ragione_socialeControl && emailControl && telefonoControl){//&& cfControl && ruoloControl && emailControl && telefonoControl) {
      const ragione_sociale = ragione_socialeControl.value;
      const email = emailControl.value;
      const telefono = telefonoControl.value;

      // Ragione sociale
      if (ragione_sociale && !FormCheck.checkRagioneSociale(ragione_sociale) ) {
        ragione_socialeControl.setErrors({ 'invalidRagioneSociale': true });
      } else {
        if (ragione_socialeControl.hasError('invalidRagioneSociale')) {
          ragione_socialeControl.setErrors(null);
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

  customValidationPassword(group: FormGroup) {
    const nuova_passwordControl = group.get('nuova_password');
    const conferma_passwordfControl = group.get('conferma_password');
    const PasswordControl = group.get('password');

    if (nuova_passwordControl && conferma_passwordfControl && PasswordControl){
      const nuova_password = nuova_passwordControl.value;
      const conferma_password = conferma_passwordfControl.value;
      const password = PasswordControl.value;

      // Password
      if (password && !FormCheck.checkNome(password)) {
        PasswordControl.setErrors({ 'invalidPassword': true });
      } else {
        if (PasswordControl.hasError('invalidPassword')) {
          PasswordControl.setErrors(null);
        }
      }

      // Conferma password
      if (conferma_password && !FormCheck.checkNome(conferma_password)) {
        conferma_passwordfControl.setErrors({ 'invalidPassword': true });
      } else {
        if (conferma_passwordfControl.hasError('invalidPassword')) {
          conferma_passwordfControl.setErrors(null);
        }
      }

      // Confronto password
      if (nuova_password && conferma_password && !FormCheck.compareTwoPasswords(nuova_password, conferma_password)) {
        conferma_passwordfControl.setErrors({ 'differentPasswords': true });
      } else {
        if (conferma_passwordfControl.hasError('differentPasswords')) {
          conferma_passwordfControl.setErrors(null);
        }
      }
    }
  }



  //----------------------------------------------------------------------

  getAzienda() {
    //Stampa i dati dell'azienda
    this.aziendeService.getProfilo().subscribe(azienda => {
      this.azienda = azienda;
      this.datiOriginali = { ...azienda };
      console.log(this.azienda)

      this.setModificaProfiloForm(); //modifica
    }) //modifica: ; this.datiOriginali={...azienda}
  }

  setModificaProfiloForm() {
    this.modificaProfiloForm.patchValue({
      // controlla che documento.tipoDocumento sia tra i tipiDocumentiAzienda
      ragione_sociale: this.azienda.ragioneSociale,
      email: this.azienda.email,
      indirizzo: this.azienda.indirizzo,
      telefono: this.azienda.telefono,
    });
  }

  controllaModifiche() {
    console.log("controllo modifiche" + JSON.stringify(this.azienda));
    //se le caselle sono vuote o se i dati sono uguali a quelli originali, il pulsante salva è disabilitato, se tornano vuote i dati originali, il pulsante è abilitato
    this.modificato = (JSON.stringify(this.azienda) != JSON.stringify(this.datiOriginali)) || this.azienda.img != ""; //modifica
  }

  onFileSelected(event: any) {
    this.file = event.target.files[0];
  }

  salvaModifiche() {
    console.log("salva modifiche");
    this.modificaAzienda(this.modificaProfiloForm.value, this.file); //modifica
    this.datiOriginali = { ...this.azienda }; //modifica
    console.log(this.modificaProfiloForm.value);
    this.modificato = false;
  }
  ModficaPassword(){
    console.log("modifica password");
    //this.modificaPassword(this.modificaPasswordForm.value);
  }

  private modificaAzienda(aziendaData: any, file: File | undefined) {
    aziendaData.piva = this.azienda.piva;
    if (this.file) {
      this.aziendeService.modificaAzienda(aziendaData, this.file).subscribe(
        response => {
          // Imposta la variabile per mostrare l'alert di successo
          this.alert.setMessage("Dipendente modificato con successo");
          this.alert.setSuccessAlert();
          this.refreshData.emit();
        },
        error => {
          // Gestisci eventuali errori
          this.alert.setMessage("Errore durante la modifica del dipendente");
          this.alert.setDangerAlert();
          this.refreshData.emit();
        });
    }
  }

}
