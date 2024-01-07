import { Injectable } from '@angular/core';

import { Observable, tap } from 'rxjs';
import { Dipendente } from '../../model/Dipendente';

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

  getMyDipendenti(): Observable<Dipendente[]> {
    return this.http.get<Dipendente[]>(Settings.API_ENDPOINT + 'my-dipendenti', { headers: this.auth.headers }).pipe(
      tap(dipendenti => this.dipendenti = dipendenti)
    )
  }

  getDipendente(id: number): Observable<Dipendente> {
    return this.http.get<Dipendente>(Settings.API_ENDPOINT + 'dipendente?id=' + id, { headers: this.auth.headers });
  }

  addDipendente(dipendente: Dipendente, file: File): Observable<any> {
    const formData: FormData = new FormData();
    
    // Aggiungi la parte 'dipendente' come Blob
    formData.append('dipendente', new Blob([JSON.stringify(dipendente)], { type: 'application/json' }));
  
    // Assicurati che il file non sia nullo prima di aggiungerlo alla FormData
    if (file) {
      // Aggiungi la parte 'file' come Blob con il nome del file
      formData.append('file', new Blob([file], { type: file.type }), file.name);
    }
    else{
      formData.append('file', new Blob());
    }

    return this.http.post(Settings.API_ENDPOINT + 'aggiungi-dipendente', formData, { headers: this.auth.headers });
  }
  
  
}
