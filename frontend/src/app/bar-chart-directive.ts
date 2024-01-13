import { Directive, ElementRef, OnInit, OnDestroy, Input } from '@angular/core';
import Chart from 'chart.js/auto';

@Directive({
  selector: '[appBarChart]'
})
export class BarChartDirective implements OnInit, OnDestroy {
  private chart: any; // Aggiungi un campo per mantenere il riferimento al grafico

  @Input() documentiValidi!: number;
  @Input() documentiInScadenza!: number;
  @Input() documentiScaduti!: number;

  constructor(private el: ElementRef) {}

  ngOnInit(): void {
    this.createBarChart();
    this.updateChartSize(); // Chiamata all'inizio per impostare le dimensioni iniziali
    window.addEventListener('resize', this.updateChartSize.bind(this)); // Aggiungi un ascoltatore per il ridimensionamento della finestra
  }

  ngOnDestroy(): void {
    window.removeEventListener('resize', this.updateChartSize.bind(this)); // Rimuovi l'ascoltatore quando il componente viene distrutto
  }

  private createBarChart(): void {
    const canvas = this.el.nativeElement;
    const ctx = canvas.getContext('2d');
  
    this.chart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: ['Validi', 'In Scadenza', 'Scaduti'],
        datasets: [{
          label: 'Stato Documenti',
          data: [this.documentiValidi, this.documentiInScadenza, this.documentiScaduti],
          backgroundColor: ['rgb(89, 189, 70)', 'rgb(255, 205, 86)', 'rgb(255, 99, 132)'],
          borderColor: ['rgb(89, 189, 70)', 'rgb(255, 205, 86)', 'rgb(255, 99, 132)'],
          borderWidth: 1
        }]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
            ticks: {
              callback: (value: any) => {
                return value.toLocaleString('en-US', { maximumFractionDigits: 0 });
              }
            }
          }
        },
        plugins: {
          legend: {
            display: false // Nascondi la legenda
          }
        }
      }
    });
  }
  

  private updateChartSize(): void {
    const canvas = this.el.nativeElement;
    const parent = canvas.parentElement;

    canvas.width = parent.offsetWidth;
    canvas.height = parent.offsetHeight;

    this.chart.resize(); // Aggiorna le dimensioni del grafico
  }
}
