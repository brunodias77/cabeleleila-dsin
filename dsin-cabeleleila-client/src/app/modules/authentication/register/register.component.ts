import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../../../services/api/api.service';
import { firstValueFrom } from 'rxjs';
import { ToastComponent } from '../../../components/ui/toast/toast.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  imports: [ToastComponent, CommonModule, FormsModule],
})
export class RegisterComponent {
  @ViewChild(ToastComponent) toastComponent!: ToastComponent;

  public formData = {
    name: '',
    phoneNumber: '',
    email: '',
    password: '',
  };

  public isLoading = false;
  public message = '';

  constructor(private apiService: ApiService, private router: Router) {}

  async handleRegister() {
    try {
      this.isLoading = true;
      const response: any = await firstValueFrom(
        this.apiService.registerUser(this.formData)
      );
      if (response.status === 201) {
        this.toastComponent.message = 'Usuário registrado com sucesso!';
        this.toastComponent.type = 'success';
        this.router.navigate(['/login']);
      }
    } catch (error: any) {
      this.toastComponent.message = error.error || 'Ocorreu um erro.';
      this.toastComponent.type = 'error';
    } finally {
      this.isLoading = false;
    }
  }
}
