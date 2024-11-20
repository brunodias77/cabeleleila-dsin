import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../../services/api/api.service';
import { RouterModule } from '@angular/router';
import { WeeklyPerformanceComponent } from './weekly-performance/weekly-performance.component';
import { CommonModule } from '@angular/common';
import { AppointmentsAdminComponent } from "./appointments-admin/appointments-admin.component";
import { CreateServicesAdminComponent } from "./create-services-admin/create-services-admin.component";

@Component({
  selector: 'app-painel',
  standalone: true,
  imports: [RouterModule, WeeklyPerformanceComponent, CommonModule, AppointmentsAdminComponent, CreateServicesAdminComponent],
  templateUrl: './painel.component.html',
  styleUrl: './painel.component.scss',
})
export class PainelComponent implements OnInit {
  currentComponent: string = 'performance'; // Pode ser alterado dinamicamente
  constructor(private api: ApiService) {}
  ngOnInit(): void {}
}
