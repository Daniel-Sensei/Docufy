import { Component, Input } from '@angular/core';
import { Documento } from '../../../model/Documento';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrl: './reports.component.css'
})
export class ReportsComponent {
  @Input() documentiValidi!: Documento[];
  @Input() documentiInScadenza!: Documento[];
  @Input() documentiScaduti!: Documento[];

}
