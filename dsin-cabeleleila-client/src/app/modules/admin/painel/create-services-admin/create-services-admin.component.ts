import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ApiService } from '../../../../services/api/api.service';

@Component({
  selector: 'app-create-services-admin',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './create-services-admin.component.html',
  styleUrl: './create-services-admin.component.scss',
})
export class CreateServicesAdminComponent {
  createServiceForm: FormGroup;

  constructor(private fb: FormBuilder, private apiService: ApiService) {
    this.createServiceForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.maxLength(200)]],
      price: ['', [Validators.required, Validators.min(0)]],
    });
  }

  getErrorMessage(controlName: string): string {
    const control = this.createServiceForm.get(controlName);
    if (control?.hasError('required')) {
      return 'Esse campo é obrigatório';
    } else if (control?.hasError('name')) {
      return 'Digte um nome válido';
    }
    return '';
  }

  createServiceOnSubimit() {
    if (this.createServiceForm.valid) {
      const request = this.createServiceForm.value;

      this.apiService.createService(request).subscribe({
        next: (response) => {
          alert('Serviço criado com sucesso!');
          this.createServiceForm.reset();
        },
        error: (error) => {
          alert('Ocorreu um erro ao tentar criar o serviço.');
        },
      });
    } else {
      alert(
        'Por favor, preencha todos os campos corretamente antes de enviar.'
      );
    }
  }
}
