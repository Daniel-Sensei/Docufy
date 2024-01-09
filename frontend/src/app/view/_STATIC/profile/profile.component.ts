import { Component } from '@angular/core';
import { Azienda } from '../../../model/Azienda';
import { AziendeService } from '../../../service/aziende/aziende.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  azienda!: Azienda;

  constructor(
    private aziendeService: AziendeService
  ) {}

  ngOnInit(): void {
    this.getAzienda();
  }

  getAzienda() {
    this.aziendeService.getProfilo().subscribe( azienda => { this.azienda = azienda})
  }

}
