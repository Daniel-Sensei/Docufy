import { Component, OnInit } from '@angular/core';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { NgFor } from '@angular/common';

//import { Dipendente } from '../../../model/Dipendente';
import { DipendentiService } from '../../../service/dipendenti/dipendenti.service';
import { AuthService } from '../../../service/auth/auth.service';



@Component({
  selector: 'app-add-dipendente-corso-modal',
  templateUrl: './add-dipendente-corso-modal.component.html',
  styleUrl: './add-dipendente-corso-modal.component.css'
})
export class AddDipendenteCorsoModalComponent implements OnInit {
  dropdownList: any[] =[];
  selectedItems: { item_id: number, item_text: string}[] = [];
  dropdownSettings: IDropdownSettings = {};

  constructor(public activeModal: NgbActiveModal, 
              private dipendentiService: DipendentiService,
              private auth: AuthService) {
  }
  

      ngOnInit(){
        this.caricaDipendenti();
        /*this.dropdownList = [
          { item_id: 1, item_text: 'Primo Dipendente'},
          { item_id: 2, item_text: 'Secondo Dipendente'},
          { item_id: 3, item_text: 'Terzo Dipendente'},
          { item_id: 4, item_text: 'Quarto Dipendente'},
          { item_id: 5, item_text: 'Quinto Dipendente'},
        ];*/
       
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
    pIva: string= this.auth.getCurrentPIva()!; //come faccio ad impostare la partita iva a quella dell'azienda corrente
   
    caricaDipendenti(){
      this.dipendentiService.getDipendenti(this.pIva).subscribe((dipendenti:any[])=>{
        this.dropdownList = dipendenti.map((dipendente, index)=>({
          item_id: index,
          item_text: dipendente.nome + " " + dipendente.cognome
        }));
      });
    }


    onItemSelect(item: any) {
      console.log('onItemSelect', item);
    }
    
    onSelectAll(items: any) {
      console.log('onSelectAll', items);
    }

    submit(){
      console.log(this.selectedItems);
      this.activeModal.close();
    }

    

}
