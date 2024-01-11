import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  showSuccessAlert = false;
  showWarningAlert = false;
  showDangerAlert = false;

  message: string = '';

  getMessages() {
    return this.message;
  }

  constructor() { }

  documentSuccess = false;
  documentWarning = false;
  documentDanger = false;

  getDocumentSuccess() {
    return this.documentSuccess;
  }

  getDocumentWarning() {
    return this.documentWarning;
  }

  getDocumentDanger() {
    return this.documentDanger;
  }

  setDocumentAlert(message: string, type: string) {
    if (type == 'success') {
      this.documentSuccess = true;
    }
    else if (type == 'warning') {
      this.documentWarning = true;
    }
    else if (type == 'danger') {
      this.documentDanger = true;
    }
    this.setMessage(message);

    setTimeout(() => {
      this.documentSuccess = false;
      this.documentWarning = false;
      this.documentDanger = false;
    }, 10000);
  }

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
