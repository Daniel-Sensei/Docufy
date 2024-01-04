import { Component } from '@angular/core';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AuthService } from '../../../service/auth.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loginForm: FormGroup;
  model: NgbDateStruct | undefined;

  success: boolean = true;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    ) {
      this.loginForm = this.fb.group({
        email: ['', [Validators.required, Validators.email, Validators.pattern('[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}')]],
        password: ['', Validators.required],
        rememberMe: [false]
      });
  }

  submitForm() {
    console.log(this.loginForm.value);
    this.auth.loginFake().subscribe( result => {
      console.log(result);
      this.success = result;
    });
  }

}
