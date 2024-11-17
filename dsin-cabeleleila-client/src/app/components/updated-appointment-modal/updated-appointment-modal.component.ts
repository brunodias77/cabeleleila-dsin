import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-updated-appointment-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './updated-appointment-modal.component.html',
  styleUrl: './updated-appointment-modal.component.scss',
})
export class UpdatedAppointmentModalComponent {
  @Input() title: string = '';
  @Output() onClose = new EventEmitter<void>();
}
