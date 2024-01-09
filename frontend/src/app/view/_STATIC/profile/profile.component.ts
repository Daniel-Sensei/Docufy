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
  datiOriginali!: Azienda; //modifica
  modificato = false;       //


  constructor(
    private aziendeService: AziendeService
  ) {}

  ngOnInit(): void {
    this.getAzienda();
    
  }

  getAzienda() {
    //Stampa i dati dell'azienda

    this.aziendeService.getProfilo().subscribe( azienda => { this.azienda = azienda; this.datiOriginali={...azienda}; console.log(this.azienda)}) //modifica: ; this.datiOriginali={...azienda}
  }

  controllaModifiche(){
    this.modificato = (JSON.stringify(this.azienda) != JSON.stringify(this.datiOriginali)) || this.azienda.img!=""; //modifica
  }

  salvaModifiche(){
    this.aziendeService.updateProfilo(this.azienda).subscribe();
    this.datiOriginali={...this.azienda}; //modifica
    this.modificato=false;
  }
}
