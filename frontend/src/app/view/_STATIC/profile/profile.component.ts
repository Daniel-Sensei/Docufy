import { Component } from '@angular/core';
import { Azienda } from '../../../model/Azienda';
import { AziendeService } from '../../../service/aziende/aziende.service';
import { AlertService } from '../../../service/alert/alert.service';

import { Output, EventEmitter } from '@angular/core'; //modifica
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { FileService } from '../../../service/file/file.service';
import { FormCheck } from '../../../FormCheck';
import { AuthService } from '../../../service/auth/auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  @Output() refreshData: EventEmitter<void> = new EventEmitter<void>();

  success: boolean = true; //PER IL CONTROLLO DELLA PASSWORD
  serverError: boolean = false;

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
    private auth: AuthService,
    private router: Router,
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
      password: ['', Validators.required, ],
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

    if (ragione_socialeControl && emailControl && telefonoControl){
      const ragione_sociale = ragione_socialeControl.value;
      const email = emailControl.value;
      const telefono = telefonoControl.value;

      // Ragione sociale
      if (ragione_sociale && !FormCheck.checkRagioneSociale(ragione_sociale) ) {
        ragione_socialeControl.setErrors({ 'invalidragione_Sociale': true });
      } else {
        if (ragione_socialeControl.hasError('invalidragione_Sociale')) {
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
        telefonoControl?.setErrors({ 'invalidTelefono': true });
      } else {
        if (telefonoControl?.hasError('invalidTelefono')) {
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
      //const password = PasswordControl.value;

      // Password
      //controllato con il service NON SERVE
      /*if (password && !FormCheck.checkNome(password)) {
        PasswordControl.setErrors({ 'invalidPassword': true });
      } else {
        if (PasswordControl.hasError('invalidPassword')) {
          PasswordControl.setErrors(null);
        }
      }*/
      // Conferma password NON SERVE
      /*if (conferma_password && !FormCheck.checkNome(conferma_password)) {
        conferma_passwordfControl.setErrors({ 'invalidPassword': true });
      } else {
        if (conferma_passwordfControl.hasError('invalidPassword')) {
          conferma_passwordfControl.setErrors(null);
        }
      }*/
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

  getAzienda() {
    this.aziendeService.getProfilo().subscribe(azienda => {
      this.azienda = azienda;
      this.datiOriginali = { ...azienda }; //modifica
      console.log(this.azienda)
      this.setModificaProfiloForm(); //modifica
    }) 
  }

  setModificaProfiloForm() {
    this.modificaProfiloForm.patchValue({
      ragione_sociale: this.azienda.ragioneSociale,
      email: this.azienda.email,
      indirizzo: this.azienda.indirizzo,
      telefono: this.azienda.telefono,
    });
  }

  controllaModifiche() { // this.modificato = (JSON.stringify(this.azienda) != JSON.stringify(this.datiOriginali)) || this.azienda.img != ""; 
    console.log("dati precedenti"+this.datiOriginali.ragioneSociale);
    console.log("controllo"+this.modificaProfiloForm.value);
    if (this.modificaProfiloForm.value.ragioneSociale != this.datiOriginali.ragioneSociale ||
      this.modificaProfiloForm.value.email != this.datiOriginali.email ||
      this.modificaProfiloForm.value.indirizzo != this.datiOriginali.indirizzo ||
      this.modificaProfiloForm.value.telefono != this.datiOriginali.telefono ||
      (this.modificaProfiloForm.value.img && this.modificaProfiloForm.value.img !== "") ||
      this.file) {
      this.modificato = true;
    } else {
      this.modificato = false;
    }
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
  modificaPassword(){
    console.log("modifica password");
    this.auth.modificaPassword(this.modificaPasswordForm.get('password')?.value, this.modificaPasswordForm.get('nuova_password')?.value).
      subscribe(
        result => {
          if (result) {
            this.router.navigate(['/']);
          }
          else {
            this.success = false;
          }
        },
        error => {
          this.serverError = true;
          //this.router.navigate(['/profile']);
        });
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

  //DA CONTINUARE 
  /*submitForm() {
    this.auth.modificaPassword(this.modificaPasswordForm.get('password')?.value, this.modificaPasswordForm.get('nuova_password')?.value).
      subscribe(
        result => {
          if (result) {
            this.router.navigate(['/']);
          }
          else {
            this.success = false;
          }
        },
        error => {
          this.serverError = true;
          //this.router.navigate(['/profile']);
        });
  }*/

}
