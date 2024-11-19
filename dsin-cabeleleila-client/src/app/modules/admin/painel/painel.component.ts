import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../../services/api/api.service';
import { WeeklyPerformance } from '../../../types';

@Component({
  selector: 'app-painel',
  standalone: true,
  imports: [],
  templateUrl: './painel.component.html',
  styleUrl: './painel.component.scss',
})
export class PainelComponent implements OnInit {
  WeeklyPerformance: WeeklyPerformance | undefined;
  constructor(private api: ApiService) {}
  ngOnInit(): void {
    this.api.getPerformaceData().subscribe((response) => {
      console.log('Response', response.data);
      this.WeeklyPerformance = response.data;
      console.log('Performace', this.WeeklyPerformance);
    });
  }
}
