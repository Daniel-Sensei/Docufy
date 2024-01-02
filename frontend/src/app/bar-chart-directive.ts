import { Directive, ElementRef, OnInit } from '@angular/core';
import Chart from 'chart.js/auto';

@Directive({
  selector: '[appBarChart]'
})
export class BarChartDirective implements OnInit {
  constructor(private el: ElementRef) {}

  ngOnInit(): void {
    this.createBarChart();
  }

  private createBarChart(): void {
    const canvas = this.el.nativeElement;
    const ctx = canvas.getContext('2d');

    new Chart(ctx, {
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
}
