import { Injectable } from '@angular/core';

import { of, Observable, tap } from 'rxjs';
import { Dipendente } from '../../model/Dipendente';
import { DIPENDENTI } from '../../model/mocks/mock-dipendenti';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';
import { Settings } from '../../Settings';

@Injectable({
  providedIn: 'root'
})
export class DipendentiService {

  dipendenti: Dipendente[] = [];

  constructor(
    private auth: AuthService,
    private http: HttpClient) { }

    getMyDipendenti(): Observable<Dipendente[]>{
      return this.http.get<Dipendente[]>(Settings.API_ENDPOINT + 'my-dipendenti', { headers: this.auth.headers }).pipe(
        tap(dipendenti => this.dipendenti = dipendenti)
      )
    }

    getDipendente(id: number): Observable<Dipendente>{
      return this.http.get<Dipendente>(Settings.API_ENDPOINT + 'dipendente?id=' + id, { headers: this.auth.headers });
    }

    getAllDipendenti(): Observable<Dipendente[]>{
      return of(DIPENDENTI);
    }
  }
