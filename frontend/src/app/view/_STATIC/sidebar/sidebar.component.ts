import { Component } from '@angular/core';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LogoutModalComponent } from '../../_LOGIN/logout-modal/logout-modal.component';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  role: string = 'C'

  constructor(private modalService: NgbModal,) { }

  logout() {
    const modalRef = this.modalService.open(LogoutModalComponent, {
      size: 'md' // 'lg' sta per grande, puoi utilizzare anche 'sm' per piccolo
    });
  }
}
