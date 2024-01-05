import { Component } from '@angular/core';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LogoutModalComponent } from '../../_LOGIN/logout-modal/logout-modal.component';
import { Router } from '@angular/router';

import { FormControl, ReactiveFormsModule } from '@angular/forms';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  text = new FormControl('');

  constructor(
    private modalService: NgbModal,
    private router: Router) { }

  toggleSidebar() {
    document.body.classList.toggle('toggle-sidebar');
  }

  search() {
    if (this.text.value === '') {
      this.router.navigate(['/']);
      return;
    }
    this.router.navigate(['/search/', this.text.value]);
  }

  logout() {
    const modalRef = this.modalService.open(LogoutModalComponent, {
      size: 'md' // 'lg' sta per grande, puoi utilizzare anche 'sm' per piccolo
    });
  }

}
