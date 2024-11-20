import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Appointment, AppointDetailsAdmin } from '../../types';
import { ButtonComponent } from '../ui/button/button.component';

@Component({
  selector: 'app-appointment-table-admin',
  standalone: true,
  imports: [CommonModule, ButtonComponent],
  templateUrl: './appointment-table-admin.component.html',
  styleUrl: './appointment-table-admin.component.scss',
})
export class AppointmentTableAdminComponent {
  @Input() appointment: AppointDetailsAdmin | undefined;
  @Output() onUpdate = new EventEmitter<Appointment>();
  @Output() onDelete = new EventEmitter<Appointment>();
  @Output() onConfirm = new EventEmitter<Appointment>();

  statusClasses(status: string) {
    return (
      {
        CONFIRMADO:
          'text-green-600 bg-green-50 px-3 py-2 rounded-full font-semibold text-xs',
        AGENDADO:
          'text-yellow-600 bg-yellow-50 px-3 py-2 rounded-full font-semibold text-xs',
        CANCELADO:
          'text-red-600 bg-red-50 px-3 py-2 rounded-full font-semibold text-xs',
      }[status] || ''
    );
  }
}
