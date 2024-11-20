import { Component, OnInit } from '@angular/core';
import {
  RequestWeeklyPerformanceAdmin,
  WeeklyPerformance,
} from '../../../../types';
import { ApiService } from '../../../../services/api/api.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { startOfWeek, endOfWeek, format } from 'date-fns';

@Component({
  selector: 'app-weekly-performance',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './weekly-performance.component.html',
  styleUrls: ['./weekly-performance.component.scss'],
})
export class WeeklyPerformanceComponent implements OnInit {
  WeeklyPerformance: WeeklyPerformance | undefined;
  startDate: string = '';
  endDate: string = '';

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.setWeekDates();
    this.loadPerformanceData();
  }

  setWeekDates() {
    const currentDate = new Date();

    const startOfWeekDate = startOfWeek(currentDate, { weekStartsOn: 0 });
    this.startDate = this.formatDate(startOfWeekDate);

    const endOfWeekDate = endOfWeek(currentDate, { weekStartsOn: 0 });
    this.endDate = this.formatDate(endOfWeekDate);
  }

  formatDate(date: Date): string {
    return format(date, 'yyyy-MM-dd');
  }

  filterDate() {
    if (this.startDate && this.endDate) {
      this.loadPerformanceData();
    } else {
      console.log('Por favor, forneça ambas as datas');
    }
  }

  loadPerformanceData() {
    var request: RequestWeeklyPerformanceAdmin = {
      startDate: this.startDate,
      endDate: this.endDate,
    };
    console.log('Enviando request', request);
    this.api.getPerformaceData(request).subscribe((response) => {
      console.log('Response', response.data);
      this.WeeklyPerformance = response.data;
    });
  }
}
