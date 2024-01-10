import { Injectable } from '@angular/core';

import { Documento } from '../../model/Documento';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';
import { Settings } from '../../Settings';

@Injectable({
  providedIn: 'root'
})
export class DocumentiService {

  constructor(
    private http: HttpClient,
    private auth: AuthService
  ) { }

  getDocumentiAzienda(pIva: string): Observable<Documento[]> {
    return this.http.get<Documento[]>(Settings.API_ENDPOINT + "documenti-azienda?pIva=" + pIva, { headers: this.auth.headers });
  }

}
