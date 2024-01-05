import { Component } from '@angular/core';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FormCheck } from '../../../FormCheck';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css'
})
export class ContactComponent {

  contactForm: FormGroup;
  model: NgbDateStruct | undefined;

  constructor(
    private fb: FormBuilder,
  ) {
    this.contactForm = this.fb.group({
      nome: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      oggetto: ['', Validators.required],
      messaggio: ['', Validators.required],
    });
  }

  submitForm() {
    console.log(this.contactForm.value);
  }
}