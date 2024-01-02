import { Injectable } from '@angular/core';

import { of, Observable } from 'rxjs';
import { Corso } from '../model/Corso';
import { CORSI } from '../model/mocks/mock-corsi';

@Injectable({
  providedIn: 'root'
})
export class CorsiService {

  constructor() { }

  getAllCorsi(): Observable<Corso[]>{
    return of(CORSI);
  }
}
