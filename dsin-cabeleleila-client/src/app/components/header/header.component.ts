import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api/api.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  username = '';
  isAdmin = false;
  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.apiService.getUserDetails().subscribe((data) => {
      var role = localStorage.getItem('role');
      if (role === 'ROLE_ADMIN') {
        console.log('Verificando se o usuário é um administrador: ');
        this.isAdmin = true;
      }
      if (data?.data?.name) {
        this.username = data.data.name;
        console.log('Verificando se o nome do usuário é exibido na tela: ');
        console.log(data.data);
      }
    });
  }
}
