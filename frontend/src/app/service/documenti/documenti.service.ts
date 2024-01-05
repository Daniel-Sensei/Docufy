import { Injectable } from '@angular/core';

import { of, Observable } from 'rxjs';
import { Documento } from '../../model/Documento';
import { DOCUMENTI } from '../../model/mocks/mock-documenti';

@Injectable({
  providedIn: 'root'
})
export class DocumentiService {

  constructor() { }

  getAllDocumenti(): Observable<Documento[]>{
    return of(DOCUMENTI);
  }

}
