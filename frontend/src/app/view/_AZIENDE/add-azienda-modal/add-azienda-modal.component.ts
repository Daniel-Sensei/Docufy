import { Component } from '@angular/core';
import { NgbActiveModal, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlertService } from '../../../service/alert/alert.service';
import { FormCheck } from '../../../FormCheck';
import { AziendeService } from '../../../service/aziende/aziende.service';

@Component({
  selector: 'app-add-azienda-modal',
  templateUrl: './add-azienda-modal.component.html',
  styleUrl: './add-azienda-modal.component.css'
})
export class AddAziendaModalComponent {

  private file: File | undefined;

  addAziendaForm: FormGroup;
  model: NgbDateStruct | undefined;

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private azienteService: AziendeService,
    ) {
    this.addAziendaForm = this.fb.group({
      ragioneSociale: ['', Validators.required],
      piva: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefono: ['', Validators.required],
      indirizzo: ['',Validators.required],
      img: ['',],
    }, { validators: this.customValidation });
  }
  
  customValidation(group: FormGroup) {
    const pivaControl = group.get('piva');
    const emailControl = group.get('email');
    const telefonoControl = group.get('telefono');

    if(pivaControl && emailControl && telefonoControl){

      //piva validation
      const piva = pivaControl.value;
      if (piva && !FormCheck.checkPIva(piva)) {
        pivaControl.setErrors({ 'invalidpiva': true });
      }
      else if(pivaControl.hasError('invalidpiva')){
        pivaControl.setErrors(null);
      }

      //email validation
      const email = emailControl.value;
      if (email && !FormCheck.checkEmail(email)) {
        emailControl.setErrors({ 'invalidEmail': true });
      }
      else if(emailControl.hasError('invalidEmail')){
        emailControl.setErrors(null);
      }

      //telefono validation
      const telefono = telefonoControl.value;
      if (telefono && !FormCheck.checkTelefono(telefono)) {
        telefonoControl.setErrors({ 'invalidTelefono': true });
      }
      else if(telefonoControl.hasError('invalidTelefono')){
        telefonoControl.setErrors(null);
      }
    }

  }


  submitForm() { 
    this.activeModal.close(this.addAziendaForm.value);
    console.log(this.addAziendaForm.value);
    this.azienteService.addAzienda(this.addAziendaForm.value, this.file).subscribe(
      response => {
        console.log("Azienda aggiunta con successo")
        console.log(response);
      },
      error => {
        console.log(error);
        console.log("Errore nell'aggiunta dell'azienda" + error.status);
      }
    );
    //this.alert.success("Azienda aggiunta con successo");
  }

  onFileSelected(event: any) {
    this.file = event.target.files[0];
  }

}
