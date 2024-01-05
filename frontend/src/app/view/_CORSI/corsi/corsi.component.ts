import { Component } from '@angular/core';

import { AddCorsoModalComponent } from '../add-corso-modal/add-corso-modal.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-corsi',
  templateUrl: './corsi.component.html',
  styleUrl: './corsi.component.css'
})
export class CorsiComponent {
  constructor(private modalService: NgbModal) {}

  openAddCorsoModal() {
    const modalRef = this.modalService.open(AddCorsoModalComponent, {
      size: 'md' 
    });
  }
}
