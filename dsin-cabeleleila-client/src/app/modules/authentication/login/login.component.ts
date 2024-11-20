import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ApiService } from '../../../services/api/api.service';
import { firstValueFrom } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  public loginForm: FormGroup;
  public message: string = '';
  public isLoading: boolean = false;

  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
    });
  }

  ngOnInit(): void {}

  async handleSubmit() {
    if (this.loginForm.invalid) {
      this.showMessage('Preencha todos os campos corretamente');
      return;
    }

    this.isLoading = true;

    try {
      const response: any = await firstValueFrom(
        this.apiService.loginUser(this.loginForm.value)
      );

      if (response.status === 200) {
        this.showMessage('Usuario logado com sucesso');
        localStorage.setItem('token', response.jwt);
        localStorage.setItem('role', response.role);

        if (response.role === 'ROLE_ADMIN') {
          this.router.navigate(['/painel']);
        } else {
          this.router.navigate(['/profile']);
        }
      }
    } catch (error: any) {
      this.showMessage(
        error.error?.message || error.message || 'Erro ao fazer login'
      );
    } finally {
      this.isLoading = false;
    }
  }

  showMessage(message: string) {
    this.message = message;
    setTimeout(() => {
      this.message = '';
    }, 4000);
  }
}
