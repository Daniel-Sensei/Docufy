import { Component, Input, OnInit, Output } from '@angular/core';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { NgFor } from '@angular/common';

import { Dipendente } from '../../../model/Dipendente';
import { DipendentiService } from '../../../service/dipendenti/dipendenti.service';
import { AuthService } from '../../../service/auth/auth.service';
import { CorsiService } from '../../../service/corsi/corsi.service';
import { ActivatedRoute } from '@angular/router';
import { EventEmitter } from '@angular/core';
import { AlertService } from '../../../service/alert/alert.service';



@Component({
  selector: 'app-add-dipendente-corso-modal',
  templateUrl: './add-dipendente-corso-modal.component.html',
  styleUrl: './add-dipendente-corso-modal.component.css'
})
export class AddDipendenteCorsoModalComponent implements OnInit {
  @Input() idCorso!: number;
  @Output() refreshData: EventEmitter<void> = new EventEmitter<void>();

  dropdownList: any[] = [];
  selectedItems: { item_id: number, item_text: string }[] = [];
  dropdownSettings: IDropdownSettings = {};

  constructor(
    public activeModal: NgbActiveModal,
    private dipendentiService: DipendentiService,
    private auth: AuthService,
    private route: ActivatedRoute,
    private corsiService: CorsiService,
    private alert: AlertService) { }


  ngOnInit() {
    this.caricaDipendenti();

    this.dropdownSettings = {
      singleSelection: false,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Select All',
      unSelectAllText: 'Unselect All',
      itemsShowLimit: 7,
      allowSearchFilter: true
    };
  }
  pIva: string = this.auth.getCurrentPIva()!;

  caricaDipendenti() {
    //TODO: prendere solo di
    this.dipendentiService.getDipendenti(this.pIva).subscribe((dipendenti: any[]) => {
      this.dropdownList = dipendenti.map((dipendente, index) => ({
        item_id: dipendente.id,
        item_text: dipendente.nome + " " + dipendente.cognome,
      }));
    });
  }


  onItemSelect(item: any) {
    //console.log('onItemSelect', item);
  }

  onSelectAll(items: any) {
    //console.log('onSelectAll', items);
  }

  submit() {
    //console.log(this.selectedItems);
    this.activeModal.close();


    const dipendenti = this.getDipendentiById();

    this.corsiService.addDipendentiCorso(this.idCorso, dipendenti).subscribe(
      (response) => {
        this.alert.setAlertCorsi("success", `Dipendenti aggiunti con successo`);
        this.refreshData.emit();
      },
      (error) => {
        this.alert.setAlertCorsi("danger", `Errore durante l'aggiunta dei dipendenti`);
      }
    );
  }

  getDipendentiById() {
    const dipendenti: any = [];
    this.selectedItems.forEach(element => {
      dipendenti.push({ id: element.item_id });
    });
    return dipendenti;
  }



}
