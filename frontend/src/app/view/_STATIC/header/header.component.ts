import { Component, Input } from '@angular/core';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LogoutModalComponent } from '../../_LOGIN/logout-modal/logout-modal.component';
import { Router, NavigationStart } from '@angular/router';

import { FormControl } from '@angular/forms';

import { AziendeService } from '../../../service/aziende/aziende.service';
import { Azienda } from '../../../model/Azienda';
import { AuthService } from '../../../service/auth/auth.service';
import { ActivatedRoute } from '@angular/router';



@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  text = new FormControl('');

  role?: string;
  azienda?: Azienda;
  aziende?: Azienda[];
  aziendaSelezionata: string = '';  // Aggiunto per memorizzare la selezione

  isDashboard: boolean = false;

  constructor(
    private modalService: NgbModal,
    private router: Router,
    private aziendeService: AziendeService,
    private auth: AuthService,
    private route: ActivatedRoute) {
      this.router.events.subscribe(event => {
        if (event instanceof NavigationStart) {
          this.isDashboard = event.url === '/'; //controllo se è login o register 
          if(!this.isDashboard){
            this.selectFirstAzienda();
          }
        }
      });
     }

  toggleSidebar() {
    document.body.classList.toggle('toggle-sidebar');
  }

  ngOnInit(): void {
    this.getAzienda();
    this.getAziende();
  }

  getAzienda(): void {
    this.aziendeService.getProfilo().subscribe(azienda => {
      this.azienda = azienda;
      this.role = this.auth.getRole() as string | undefined; // Fix: Update the type to allow undefined values
    });
  }

  getAziende(): void {
    this.aziendeService.getAziende().subscribe(
      aziende => {
        this.aziende = aziende;

        this.selectFirstAzienda();
      }
    );
  }

  selectFirstAzienda(): void {
    if (this.aziende && this.aziende.length > 0) {
      let pIva = this.auth.getSelectedPIva();
      if (pIva) {
        let index = this.aziende.findIndex(azienda => azienda.piva === pIva);
        if (index >= 0) {
          this.aziendaSelezionata = this.aziende[index].ragioneSociale;
          this.auth.setSelectedPIva(this.aziende[index].piva);
        }
      }
      else {
        this.aziendaSelezionata = this.aziende[0].ragioneSociale;
        this.auth.setSelectedPIva(this.aziende[0].piva);
      }
    }
  }

  onAziendaChange(){
    let index = this.aziende?.findIndex(azienda => azienda.ragioneSociale === this.aziendaSelezionata);
    if (index !== undefined && index >= 0) {
      this.auth.setSelectedPIva(this.aziende![index].piva);
    }

    // Ricarica la pagina
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    const currentUrl = this.router.url + '?';
    this.router.navigateByUrl(currentUrl).then(() => {
      this.router.navigated = false;
      this.router.navigate([this.router.url]);
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
