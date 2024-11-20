import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api/api.service';
import { CommonModule } from '@angular/common';
import { UserDetails } from '../../types';

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
    this.apiService.getUserDetails().subscribe((data: UserDetails) => {
      var role = localStorage.getItem('role');
      if (data.data.role[0].name === 'ROLE_ADMIN') {
        this.isAdmin = true;
      }
      if (data.data.name) {
        this.username = data.data.name;
      }
    });
  }

  logout() {
    localStorage.clear();
    window.location.href = '/login';
  }
}
