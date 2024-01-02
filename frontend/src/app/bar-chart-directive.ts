import { Directive, ElementRef, OnInit, OnDestroy } from '@angular/core';
import Chart from 'chart.js/auto';

@Directive({
  selector: '[appBarChart]'
})
export class BarChartDirective implements OnInit, OnDestroy {
  private chart: any; // Aggiungi un campo per mantenere il riferimento al grafico

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
        labels: ['Red', 'Blue', 'Yellow'],
        datasets: [{
          label: 'My First Dataset',
          data: [300, 50, 100],
          backgroundColor: ['rgb(255, 99, 132)', 'rgb(54, 162, 235)', 'rgb(255, 205, 86)'],
          borderColor: ['rgb(255, 99, 132)', 'rgb(54, 162, 235)', 'rgb(255, 205, 86)'],
          borderWidth: 1
        }]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
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
