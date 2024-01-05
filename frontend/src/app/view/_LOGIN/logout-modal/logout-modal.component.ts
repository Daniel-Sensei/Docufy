import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from '../../../service/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout-modal',
  templateUrl: './logout-modal.component.html',
  styleUrl: './logout-modal.component.css'
})
export class LogoutModalComponent {

  constructor(
    public activeModal: NgbActiveModal,
    private auth: AuthService,
    private router: Router) { }

  logout() {
    this.auth.logout();
    this.activeModal.close();
    this.router.navigate(['/login']);
  }

}
