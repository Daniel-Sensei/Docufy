import { Component, Input } from '@angular/core';

import { NgbActiveModal, NgbDate, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlertService } from '../../../service/alert/alert.service';
import { Corso } from '../../../model/Corso'
import { CorsiService } from '../../../service/corsi/corsi.service';
import { AbstractControl } from '@angular/forms';
import { AuthService } from '../../../service/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-corso-modal',
  templateUrl: './add-corso-modal.component.html',
  styleUrl: './add-corso-modal.component.css'
})
export class AddCorsoModalComponent {

  @Input() corso?: Corso;

  addCorsoForm: FormGroup;
  model: NgbDateStruct | undefined;

  categorie: string[] = ["Benessere", "Formazione", "Aggiornamento", "Lingue", "Tecnologia", "Altro"];


  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private alert: AlertService,
    private corsiService: CorsiService,
    private auth: AuthService,
    private router: Router) {
    this.addCorsoForm = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(1),]],
      categoria: ['', [Validators.required, Validators.minLength(1),]],
      prezzo: ['', [Validators.required, Validators.minLength(1), this.validateDecimal]],
      durata: ['', [Validators.required, Validators.minLength(1), this.validateInteger]],
      posti: ['', [Validators.required, Validators.minLength(1), this.validateInteger]],
      descrizione: ['', [Validators.required, Validators.minLength(1)]],
      esameFinale: [false],
    },
    );
  }

  ngOnInit(): void {
    if (this.corso != undefined) {
      this.addCorsoForm.patchValue(this.corso);
    }
  }


  submitForm() {
    this.activeModal.close('Save click');

    //stampa a console i valori del form
    console.log(this.addCorsoForm.value);
    if(this.corso == undefined){
      this.addCorso();
    }
    else{
      this.updateCorso();
    }
  }

  private addCorso() {
    this.corsiService.addCorso(this.addCorsoForm.value, this.auth.getCurrentPIva()!).subscribe(
      data => {
        this.alert.setMessage("Corso aggiunto con successo");
        this.alert.setSuccessAlert();
        console.log("Corso aggiunto con successo");
      },
      error => {
        if(error.status == 401){
          this.router.navigate(['/401']);
        }
      }
    );
  }

  private updateCorso() {
    this.corsiService.updateCorso(this.addCorsoForm.value, this.auth.getCurrentPIva()!).subscribe(
      data => {
        this.alert.setMessage("Corso modificato con successo");
        this.alert.setSuccessAlert();
        console.log("Corso modificato con successo");
      },
      error => {
        if(error.status == 401){
          this.router.navigate(['/401']);
        }
      }
    );
  }

  validateDecimal(control: AbstractControl): { [key: string]: any } | null {
    const value = control.value;
    if (value === null || value === undefined || value === '') {
      return null; // Non fare nulla se il campo è vuoto
    }

    const decimalPart = (value.toString().split('.')[1] || []).length;
    if (decimalPart > 2) {
      return { 'invalidDecimal': true };
    }

    return null;
  }

  //validate Integer
  validateInteger(control: AbstractControl): { [key: string]: any } | null {
    const value = control.value;
    if (value === null || value === undefined || value === '') {
      return null; // Non fare nulla se il campo è vuoto
    }

    const decimalPart = (value.toString().split('.')[1] || []).length;
    if (decimalPart > 0) {
      return { 'invalidInteger': true };
    }

    return null;
  }

}
