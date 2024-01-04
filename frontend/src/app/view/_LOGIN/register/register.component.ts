import { Component } from '@angular/core';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  registerForm: FormGroup;
  model: NgbDateStruct | undefined;

  constructor(
    private fb: FormBuilder,
    ) {
    this.registerForm = this.fb.group({
      nome: ['', Validators.required],
      username: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      password_confirmation: ['', Validators.required],
      terms: [false, Validators.requiredTrue],
    },); 
  }

  customValidation(group: FormGroup) {
    const passwordControl = group.get('password');
    const passwordConfirmationControl = group.get('password_confirmation');
    if (passwordControl && passwordConfirmationControl) {
      const password = passwordControl.value;
      const passwordConfirmation = passwordConfirmationControl.value;
      if (password !== passwordConfirmation) {
        passwordConfirmationControl.setErrors({ 'passwordMismatch': true });
      } else {
        // Rimuovi l'errore se la password è valida
        if (passwordConfirmationControl.hasError('passwordMismatch')) {
          passwordConfirmationControl.setErrors(null);
        }
      }
    }
  }

  submitForm() {
    if(this.registerForm.valid) return ;
    //stampa a console i valori del form
    console.log(this.registerForm.value);
  }

}