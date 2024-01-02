import { Component } from '@angular/core';
import { Corso } from '../model/Corso';
import { CorsiService } from '../service/corsi.service';

@Component({
  selector: 'app-corsi-card',
  templateUrl: './corsi-card.component.html',
  styleUrl: './corsi-card.component.css'
})
export class CorsiCardComponent {
  constructor(private corsiService: CorsiService) {}

  corsi: Corso[]=[];

  ngOnInit(): void {
    this.corsiService.getAllCorsi().subscribe(result => this.corsi = result);
  }

}
