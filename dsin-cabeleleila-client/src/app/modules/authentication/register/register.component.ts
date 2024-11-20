import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../../../services/api/api.service';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { firstValueFrom } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register.component.html',
  imports: [FormsModule, CommonModule, ReactiveFormsModule],
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  isLoading = false;
  message = '';

  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      phoneNumber: [
        '',
        [Validators.required, Validators.pattern(/^\(\d{2}\)\d{5}\d{4}$/)],
      ],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  ngOnInit(): void {}

  async handleRegister() {
    if (this.registerForm.invalid) {
      return;
    }

    try {
      this.isLoading = true;
      const response: any = await firstValueFrom(
        this.apiService.registerUser(this.registerForm.value)
      );
      if (response.status === 201) {
        this.router.navigate(['/login']);
      }
    } catch (error: any) {
      this.message = 'Erro ao registrar. Tente novamente.';
    } finally {
      this.isLoading = false;
    }
  }
}
