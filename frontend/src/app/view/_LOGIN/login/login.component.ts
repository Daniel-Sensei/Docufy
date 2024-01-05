import { Component } from '@angular/core';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AuthService } from '../../../service/auth/auth.service';
import { Router } from '@angular/router';


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
    private router: Router,
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
    this.auth.login(this.loginForm.get('email')?.value, this.loginForm.get('password')?.value, this.loginForm.get('rememberMe')?.value).
    subscribe( result => {
      if (result) {
        this.router.navigate(['/']);
      }
      else {
        this.success = false;
      }
    });
  }

}
