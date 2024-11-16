import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ApiService } from '../../../services/api/api.service';
import { firstValueFrom } from 'rxjs';
import { ToastComponent } from '../../../components/ui/toast/toast.component';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, ToastComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  constructor(private apiService: ApiService, private router: Router) {}

  public formData: any = {
    email: '',
    password: '',
  };
  public message: string = '';
  public isLoading: boolean = false;
  async handleSubmit() {
    if (!this.formData.email || !this.formData.password) {
      this.showMessage('Preencha todos os campos');
      return;
    }
    this.isLoading = true;
    try {
      const response: any = await firstValueFrom(
        this.apiService.loginUser(this.formData)
      );
      if (response.status === 200) {
        console.log(response);
        this.showMessage('User Successfully logged in');
        localStorage.setItem('token', response.jwt);
        localStorage.setItem('role', response.role);
        this.router.navigate(['/profile']);
      }
    } catch (error: any) {
      this.showMessage(
        error.error?.message || error.message || 'Erro ao fazer login'
      );
    }
  }
  showMessage(message: string) {
    this.message = message;
    setTimeout(() => {
      this.message = '';
    }, 4000);
  }
}
