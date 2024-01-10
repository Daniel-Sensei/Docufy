import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';
import { Corso } from '../../model/Corso';

import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';
import { Settings } from '../../Settings';

import { CORSI } from '../../model/mocks/mock-corsi';

@Injectable({
  providedIn: 'root'
})
export class CorsiService {

  constructor(
    private http: HttpClient,
    private auth: AuthService
  ) { }

  getCorsiProposti(pIva: string): Observable<Corso[]>{
    return this.http.get<Corso[]>(Settings.API_ENDPOINT + "corsi-proposti?pIva=" + pIva, { headers: this.auth.headers });
  }

  getAllCorsi(): Observable<Corso[]>{
    return of(CORSI);
  }
}
