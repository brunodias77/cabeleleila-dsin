import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ServiceModal } from '../../types';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-create-appointment-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, FormsModule],
  templateUrl: './create-appointment-form.component.html',
  styleUrl: './create-appointment-form.component.scss',
})
export class CreateAppointmentFormComponent {
  @Input() services: ServiceModal[] = [];
  @Input() appointmentDate: string | null = null;
  @Output() formSubmit = new EventEmitter<any>();
  @Output() cancel = new EventEmitter<void>();

  form!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      date: [this.appointmentDate || '', Validators.required],
      selectedServices: [[], Validators.required],
    });
  }

  onSubmit(): void {
    if (this.form.invalid) return;
    this.formSubmit.emit(this.form.value);
  }

  onCancel(): void {
    this.cancel.emit();
  }
}
