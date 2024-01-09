import { Injectable, Inject, PLATFORM_ID } from '@angular/core';

import { Settings } from '../../Settings'
import { AuthToken, Azienda } from '../../model/Azienda';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { isPlatformBrowser } from '@angular/common';

import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(@Inject(PLATFORM_ID) private platformId: Object, private http: HttpClient) { }

  token?: string | null;
  role?: string | null;

  currentPIva?: string | null;
  selectedPIva?: string | null;

  headers = new HttpHeaders().set('Authorization', `Basic ${this.getToken()}`);

  autologin(): Observable<boolean> {
    if (isPlatformBrowser(this.platformId)) {
      var email = localStorage.getItem('admin-email');
      var password = localStorage.getItem('admin-password');

      if(email == null || password == null){
        email = sessionStorage.getItem('admin-email');
        password = sessionStorage.getItem('admin-password');
      }
      //console.log("AUTOLOGIN" + email + password);
  
      if (email && password) {
        return this.login(email, password, false);
      } else {
        return of(false);
      }
    }

    else {
      return of(false);
    }
  }

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

  getRole(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      if (this.role === undefined) {
        this.role = sessionStorage.getItem('admin-role');
        if (this.role === null) {
          this.role = localStorage.getItem('admin-role');
        }
      }
      return this.role;
    } else {
      return null;
    }
  }

  setRole(role: string, rememberMe: boolean): void {
    this.role = role;
    if (rememberMe) {
      localStorage.setItem('admin-role', role);
    }
    sessionStorage.setItem('admin-role', role);
  }

  getCurrentPIva(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      if (this.currentPIva === undefined) {
        this.currentPIva = sessionStorage.getItem('admin-currentPIva');
      }
      return this.currentPIva;
    } else {
      return null;
    }
  }

  setCurrentPIva(rememberMe: boolean): void {
    this.headers = new HttpHeaders().set('Authorization', `Basic ${this.getToken()}`);

    if (this.getRole() == 'A') {
      this.http.get<any>(Settings.API_ENDPOINT + 'profilo', { headers: this.headers }).subscribe(
        (azienda) => {
          this.currentPIva = azienda.piva;
          if (this.currentPIva != null) {
            sessionStorage.setItem('admin-currentPIva', this.currentPIva);
            if (rememberMe) {
              localStorage.setItem('admin-currentPIva', this.currentPIva);
            }
          }
        }
      );
    }
    else if (this.getRole() == 'C') {
      this.http.get<any>(Settings.API_ENDPOINT + 'aziende', { headers: this.headers }).subscribe(
        (aziende) => {
          this.currentPIva = aziende[0].piva;
          if (this.currentPIva != null) {
            sessionStorage.setItem('admin-currentPIva', this.currentPIva);
            if (rememberMe) {
              localStorage.setItem('admin-currentPIva', this.currentPIva);
            }
          }
        }
      );
    }
  }

  login(email: string, password: string, rememberMe: boolean): Observable<boolean> {
    const user = { email: email, password: password };

    return this.http.post<AuthToken>(Settings.API_ENDPOINT + 'login', user)
      .pipe(
        map(response => {
          if (response == undefined) {
            return false;
          }
          // set email and password in local storage
          this.setEmailPassword(email, password, rememberMe);

          this.setToken(response.token, rememberMe);
          this.setRole(response.role, rememberMe);
          this.setCurrentPIva(rememberMe);

          //stampa valori di tutti i token da session sotrage e local storage
          /*
          console.log("LOGIN");
          console.log("TOKEN: " + "Session" + sessionStorage.getItem('admin-token') + " Local" + localStorage.getItem('admin-token'));
          console.log("ROLE: " + "Session" + sessionStorage.getItem('admin-role') + " Local" + localStorage.getItem('admin-role'));
          console.log("CURRENTPIVA: " + "Session" + sessionStorage.getItem('admin-currentPIva') + " Local" + localStorage.getItem('admin-currentPIva'));
          console.log("EMAIL: " + "Session" + sessionStorage.getItem('admin-email') + " Local" +  localStorage.getItem('admin-email'));
          console.log("PASSWORD: " + "Session" + sessionStorage.getItem('admin-password') + " Local" + localStorage.getItem('admin-password'));
          */
          return true;
        })
      );
  }

  logout(): void {
    this.token = null;
    sessionStorage.removeItem('admin-token');
    localStorage.removeItem('admin-token');

    this.role = null;
    sessionStorage.removeItem('admin-role');
    localStorage.removeItem('admin-role');

    this.currentPIva = null;
    sessionStorage.removeItem('admin-currentPIva');
    localStorage.removeItem('admin-currentPIva');

    sessionStorage.removeItem('admin-email');
    localStorage.removeItem('admin-email');
    sessionStorage.removeItem('admin-password');
    localStorage.removeItem('admin-password');

    /*
    console.log("LOGOUT");
    //stampa valori di tutti i token da session sotrage e local storage
    console.log("TOKEN: " + "Session" + sessionStorage.getItem('admin-token') + " Local" + localStorage.getItem('admin-token'));
    console.log("ROLE: " + "Session" + sessionStorage.getItem('admin-role') + " Local" + localStorage.getItem('admin-role'));
    console.log("CURRENTPIVA: " + "Session" + sessionStorage.getItem('admin-currentPIva') + " Local" + localStorage.getItem('admin-currentPIva'));
    console.log("EMAIL: " + "Session" + sessionStorage.getItem('admin-email') + " Local" +  localStorage.getItem('admin-email'));
    console.log("PASSWORD: " + "Session" + sessionStorage.getItem('admin-password') + " Local" + localStorage.getItem('admin-password'));
    */
  }

  setEmailPassword(email: string, password: string, rememberMe: boolean): void {
    if (rememberMe) {
      localStorage.setItem('admin-email', email);
      localStorage.setItem('admin-password', password);
    }
    sessionStorage.setItem('admin-email', email);
    sessionStorage.setItem('admin-password', password);
  }

  getSelectedPIva(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      if (this.selectedPIva === undefined) {
        this.selectedPIva = sessionStorage.getItem('admin-selectedPIva');
      }
      return this.selectedPIva;
    } else {
      return null;
    }
  }

  setSelectedPIva(pIva: string): void {
    this.selectedPIva = pIva;
    sessionStorage.setItem('admin-selectedPIva', pIva);
  }
}
