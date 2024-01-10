import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';
import { Corso } from '../../model/Corso';

import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';
import { Settings } from '../../Settings';

import { CORSI } from '../../model/mocks/mock-corsi';
import { Dipendente } from '../../model/Dipendente';

@Injectable({
  providedIn: 'root'
})
export class CorsiService {

  constructor(
    private http: HttpClient,
    private auth: AuthService
  ) { }

  getCorsiProposti(): Observable<Corso[]>{
    return this.http.get<Corso[]>(Settings.API_ENDPOINT + "corsi-proposti", { headers: this.auth.headers });
  }

  getCorsiAcquistati(pIva: string): Observable<Corso[]>{
    return this.http.get<Corso[]>(Settings.API_ENDPOINT + "corsi-acquistati?pIva=" + pIva, { headers: this.auth.headers });
  }

  getCorsiFrequentati(id: number): Observable<Corso[]>{
    return this.http.get<Corso[]>(Settings.API_ENDPOINT + "corsi-frequentati?id=" + id, { headers: this.auth.headers });
  }

  getCorso(id: number): Observable<Corso>{
    return this.http.get<Corso>(Settings.API_ENDPOINT + "corso?id=" + id, { headers: this.auth.headers });
  }

  addDipendentiCorso(id: number, dipendenti: Dipendente[]): Observable<any>{
    return this.http.post(Settings.API_ENDPOINT + "dipendenti-corso?id=" + id, dipendenti, { headers: this.auth.headers });
  }

  addAziendaCorso(id: number, pIva: string): Observable<any>{
    return this.http.post(Settings.API_ENDPOINT + "azienda-corso?id=" + id + "?pIva=" +  pIva, { headers: this.auth.headers });
  }

  addCorso(corso: Corso): Observable<any>{
    return this.http.post(Settings.API_ENDPOINT + "aggiungi-corso", corso, { headers: this.auth.headers });
  }

  updateCorso(corso: Corso): Observable<any>{
    return this.http.post(Settings.API_ENDPOINT + "modifica-corso", corso, { headers: this.auth.headers });
  }

  deleteCorso(id: number): Observable<any>{
    return this.http.delete(Settings.API_ENDPOINT + "rimuovi-corso?id=" + id, { headers: this.auth.headers });
  }

  getAllCorsi(): Observable<Corso[]>{
    return of(CORSI);
  }
}
