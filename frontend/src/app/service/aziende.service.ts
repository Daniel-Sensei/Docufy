import { Injectable } from '@angular/core';

import { of, Observable } from 'rxjs';
import { Azienda } from '../model/Azienda';
import { AZIENDE } from '../model/mocks/mock-aziende';

@Injectable({
  providedIn: 'root'
})
export class AziendeService {

  constructor() { }

  getAllAziende(): Observable<Azienda[]>{
    return of(AZIENDE);
  }
}
