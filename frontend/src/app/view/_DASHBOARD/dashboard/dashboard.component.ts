import { Component } from '@angular/core';
import { AziendeService } from '../../../service/aziende/aziende.service';
import { Azienda } from '../../../model/Azienda';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  constructor(private aziendeService: AziendeService) { }

  aziende?: Azienda[];

  ngOnInit(): void {
    if (this.aziendeService.role == 'C') {
      this.getAziende();
    }
  }

  getAziende(): void {
    this.aziendeService.getAziende().subscribe(aziende => {this.aziende = aziende;});
  }

}
