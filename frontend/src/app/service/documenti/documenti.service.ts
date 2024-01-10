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

  getDocumentiDipendente(id: number): Observable<Documento[]> {
    return this.http.get<Documento[]>(Settings.API_ENDPOINT + "documenti-dipendente?id=" + id, { headers: this.auth.headers });
  }

  getDocumento(id: number): Observable<Documento> {
    return this.http.get<Documento>(Settings.API_ENDPOINT + "documento?id=" + id, { headers: this.auth.headers });
  }

  addDocumento(documento: Documento, file: File){
    const formData: FormData = new FormData();

    formData.append('documento', new Blob([JSON.stringify(documento)], { type: 'application/json' }));

    if(file){
      formData.append('file', file, file.name);
    }
    else{
      formData.append('file', new Blob());
    }

    return this.http.post(Settings.API_ENDPOINT + "aggiungi-documento", formData, { headers: this.auth.headers });
  }

  updateDocumento(documento: Documento, file: File){
    const formData: FormData = new FormData();

    formData.append('documento', new Blob([JSON.stringify(documento)], { type: 'application/json' }));

    if(file){
      formData.append('file', file, file.name);
    }
    else{
      formData.append('file', new Blob());
    }

    return this.http.post(Settings.API_ENDPOINT + "aggiorna-documento", formData, { headers: this.auth.headers });
  }

  deleteDocumento(id: number){
    return this.http.delete(Settings.API_ENDPOINT + "rimuovi-documento?id=" + id, { headers: this.auth.headers });
  }

}
