import { Component } from '@angular/core';
import { Dipendente } from '../../../model/Dipendente';

import { Router, ActivatedRoute } from '@angular/router';
import { DipendentiService } from '../../../service/dipendenti/dipendenti.service';

import { DomSanitizer } from '@angular/platform-browser';


@Component({
  selector: 'app-dipendenti-dettagli',
  templateUrl: './dipendenti-dettagli.component.html',
  styleUrl: './dipendenti-dettagli.component.css'
})
export class DipendentiDettagliComponent {

  dipendente!: Dipendente;

  constructor(
    private route: ActivatedRoute,
    private dipendentiService: DipendentiService,
    private router: Router,
    private sanitizer: DomSanitizer
  ) { }

  ngOnInit(): void {
    this.getDipendente();
  }

  getDipendente(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.dipendentiService.getDipendente(id).subscribe(dipendente => {
      this.dipendente = dipendente;
      if (this.dipendente == undefined) {
        this.router.navigate(['/404']);
      }
      if (this.dipendente.img != '') this.dipendente.img = this.sanitizer.bypassSecurityTrustResourceUrl(this.dipendente.img) as string;
    });
  }
}
