import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  showSuccessAlert = false;
  showWarningAlert = false;
  showDangerAlert = false;

  constructor() { }

  setSuccessAlert() {
    this.showSuccessAlert = true;
    //imposta this.showSuccessAlert a false dopo 3 secondi
    setTimeout(() => {
      this.showSuccessAlert = false;
    }, 3000);
  }

  setWarningAlert() {
    this.showWarningAlert = true;
  }

  setDangerAlert() {
    this.showDangerAlert = true;
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
}
