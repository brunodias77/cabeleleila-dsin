import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ButtonComponent } from "../ui/button/button.component";

@Component({
  selector: 'app-appointment-modal',
  standalone: true,
  imports: [CommonModule, ButtonComponent],
  templateUrl: './appointment-modal.component.html',
  styleUrl: './appointment-modal.component.scss',
})
export class AppointmentModalComponent {
 @Input() title: string = '';
  @Output() onClose = new EventEmitter<void>(); 
}
