import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { Azienda } from '../../model/Azienda';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';
import { Settings } from '../../Settings';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AziendeService {

  myAziende?: Azienda[];

  azienda?: Azienda;
  role?: string;

  constructor(
    private auth: AuthService,
    private http: HttpClient) { }

  getAziende(): Observable<Azienda[]> {
    return this.http.get<Azienda[]>(Settings.API_ENDPOINT + 'aziende', { headers: this.auth.headers}).pipe(
      tap(aziende => this.myAziende = aziende)
    );
  }

  getProfilo(): Observable<Azienda> {
    return this.http.get<Azienda>(Settings.API_ENDPOINT + 'profilo', { headers: this.auth.headers}).pipe(
      tap(azienda => this.azienda = azienda),
      tap(azienda => this.role = this.auth.getRole() as string | undefined),
    );
  }

  getAzienda(pIva: String): Observable<Azienda> {
    return this.http.get<Azienda>(Settings.API_ENDPOINT + 'azienda?pIva=' + pIva, { headers: this.auth.headers});
  }

  updateProfilo(azienda: Azienda): Observable<any> { //DA MODIFICARE
    return this.http.put(Settings.API_ENDPOINT + 'profilo', azienda, { headers: this.auth.headers});
  }
}
