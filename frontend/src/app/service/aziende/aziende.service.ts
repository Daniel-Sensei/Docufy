import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { Azienda } from '../../model/Azienda';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';
import { Settings } from '../../Settings';

@Injectable({
  providedIn: 'root'
})
export class AziendeService {

  constructor(
    private auth: AuthService,
    private http: HttpClient) { }

  getAziende(): Observable<Azienda[]> {
    return this.http.get<Azienda[]>(Settings.API_ENDPOINT + 'aziende', { headers: this.auth.headers});
  }

  getProfilo(): Observable<Azienda> {
    return this.http.get<Azienda>(Settings.API_ENDPOINT + 'profilo', { headers: this.auth.headers});
  }

  getAzienda(pIva: String): Observable<Azienda> {
    return this.http.get<Azienda>(Settings.API_ENDPOINT + 'azienda?pIva=' + pIva, { headers: this.auth.headers});
  }
}
