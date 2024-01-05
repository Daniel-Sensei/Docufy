import { Injectable } from '@angular/core';

import { of, Observable } from 'rxjs';
import { Dipendente } from '../../model/Dipendente';
import { DIPENDENTI } from '../../model/mocks/mock-dipendenti';

@Injectable({
  providedIn: 'root'
})
export class DipendentiService {

  constructor() { }

  getAllDipendenti(): Observable<Dipendente[]>{
    return of(DIPENDENTI);
  }
}
