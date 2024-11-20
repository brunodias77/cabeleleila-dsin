import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../../../services/api/api.service';
import { firstValueFrom } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  imports: [CommonModule, FormsModule],
})
export class RegisterComponent {
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
        this.router.navigate(['/login']);
      }
    } catch (error: any) {
    } finally {
      this.isLoading = false;
    }
  }
}
