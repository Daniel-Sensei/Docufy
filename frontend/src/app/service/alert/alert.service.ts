import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  showSuccessAlert = false;
  showWarningAlert = false;
  showDangerAlert = false;

  message: string = '';

  constructor() { }

  setSuccessAlert() {
    this.showSuccessAlert = true;
    //imposta this.showSuccessAlert a false dopo 5 secondi
    setTimeout(() => {
      this.showSuccessAlert = false;
    }, 10000);
  }

  setWarningAlert() {
    this.showWarningAlert = true;
  }

  setDangerAlert() {
    this.showDangerAlert = true;
    setTimeout(() => {
      this.showDangerAlert = false;
    }, 10000);
  }

  getSuccessAlert() {
    return this.showSuccessAlert;
  }

  getWarningAlert() {
    return this.showWarningAlert;
  }

  getDangerAlert() {
    return this.showDangerAlert;
  }

  setMessage(message: string) {
    this.message = message;
  }

  getMessage() {
    return this.message;
  }
}
