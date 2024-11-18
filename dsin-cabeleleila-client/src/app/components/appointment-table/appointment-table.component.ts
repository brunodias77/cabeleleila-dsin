import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Appointment } from '../../types';
import { ButtonComponent } from '../ui/button/button.component';

@Component({
  selector: 'app-appointment-table',
  standalone: true,
  imports: [CommonModule, ButtonComponent],
  templateUrl: './appointment-table.component.html',
  styleUrl: './appointment-table.component.scss',
})
export class AppointmentTableComponent {
  @Input() appointments: Appointment[] = [];
  @Output() onUpdate = new EventEmitter<Appointment>();
  @Output() onDelete = new EventEmitter<Appointment>();

  statusClasses(status: string) {
    return (
      {
        AGENDADO:
          'text-green-600 bg-green-50 px-3 py-2 rounded-full font-semibold text-xs',
        CANCELADO:
          'text-red-600 bg-red-50 px-3 py-2 rounded-full font-semibold text-xs',
      }[status] || ''
    );
  }
}
