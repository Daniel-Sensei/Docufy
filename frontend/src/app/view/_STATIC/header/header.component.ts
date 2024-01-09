import { Component, Input } from '@angular/core';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LogoutModalComponent } from '../../_LOGIN/logout-modal/logout-modal.component';
import { Router } from '@angular/router';

import { FormControl } from '@angular/forms';

import { AziendeService } from '../../../service/aziende/aziende.service';
import { Azienda } from '../../../model/Azienda';
import { AuthService } from '../../../service/auth/auth.service';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  text = new FormControl('');

  role?: string;
  azienda?: Azienda;

  constructor(
    private modalService: NgbModal,
    private router: Router,
    private aziendeService: AziendeService,
    private auth: AuthService) { }

  toggleSidebar() {
    document.body.classList.toggle('toggle-sidebar');
  }

  ngOnInit(): void {
    this.getAzienda();
  }

  getAzienda(): void {
    this.aziendeService.getProfilo().subscribe(azienda => {
      this.azienda = azienda;
      this.role = this.auth.getRole() as string | undefined; // Fix: Update the type to allow undefined values
    });
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
