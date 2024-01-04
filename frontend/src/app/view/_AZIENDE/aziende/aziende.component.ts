import { Component } from '@angular/core';
import { AlertService } from '../../../alert.service';

import { AziendeService } from '../../../service/aziende.service';
import { Azienda } from '../../../model/Azienda';

@Component({
  selector: 'app-aziende',
  templateUrl: './aziende.component.html',
  styleUrl: './aziende.component.css'
})
export class AziendeComponent {

  constructor(
    public alert: AlertService,
    private aziendeService: AziendeService
    ) { }

  aziende: Azienda[] = [];

  ngOnInit(): void {
    this.aziendeService.getAllAziende().subscribe(aziende => { this.aziende = aziende; });
  }

  openAddAzienda(){}

}
