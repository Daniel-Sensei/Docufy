import { Injectable, Inject, PLATFORM_ID } from '@angular/core';

import { Settings } from '../Settings'
import { AuthToken } from '../model/Azienda';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(@Inject(PLATFORM_ID) private platformId: Object, private http: HttpClient) { }

  token?: string | null;

  getToken(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      if (this.token === undefined) {
        this.token = sessionStorage.getItem('admin-token');
        if (this.token === null) {
          this.token = localStorage.getItem('admin-token');
        }
      }
      return this.token;
    } else {
      return null; // Handle non-browser environment
    }
  }

  setToken(token: string, rememberMe: boolean): void {
    this.token = token;
    if (rememberMe) {
      localStorage.setItem('admin-token', token);
    }
    sessionStorage.setItem('admin-token', token);
  }

  login(email: string, password: string, rememberMe: boolean): Observable<boolean> {
    const user = { id: 0, email: email, password: password };

    return this.http.post<AuthToken>(Settings.API_ENDPOINT + 'login', user)
      .pipe(
        map(response => {
          this.setToken(response.token, rememberMe);
          return true;
        })
      );
  }

  //finta login per test
  loginFake(): Observable<boolean>{
    return of(false);
  }

  logout(): void {
    this.token = null;
    sessionStorage.removeItem('admin-token');
    localStorage.removeItem('admin-token');
  }
}
