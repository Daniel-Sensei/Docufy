import { Component, OnInit } from '@angular/core';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-add-dipendente-corso-modal',
  templateUrl: './add-dipendente-corso-modal.component.html',
  styleUrl: './add-dipendente-corso-modal.component.css'
})
export class AddDipendenteCorsoModalComponent implements OnInit {
  dropdownList: any[] =[];
  selectedItems: { item_id: number, item_text: string}[] = [];
  dropdownSettings: IDropdownSettings = {};

  constructor(public activeModal: NgbActiveModal) {
  }
  

      ngOnInit(){
        this.dropdownList = [
          { item_id: 1, item_text: 'Primo Dipendente'},
          { item_id: 2, item_text: 'Secondo Dipendente'},
          { item_id: 3, item_text: 'Terzo Dipendente'},
          { item_id: 4, item_text: 'Quarto Dipendente'},
          { item_id: 5, item_text: 'Quinto Dipendente'},
        ]
        
        this.dropdownSettings = {
          singleSelection: false,
          idField: 'item_id',
          textField: 'item_text',
          selectAllText: 'Select All',
          unSelectAllText: 'Unselect All',
          itemsShowLimit: 3,
          allowSearchFilter: true
        };
    }

    onItemSelect(item: any) {
      console.log('onItemSelect', item);
    }
    onSelectAll(items: any) {
      console.log('onSelectAll', items);
    }

    addDipendenteCorso(){
      
      console.log(this.selectedItems);

      this.activeModal.close();
      

      this.selectedItems.forEach(element => console.log(element.item_text));

    }

    

}
