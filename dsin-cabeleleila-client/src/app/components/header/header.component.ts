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
        this.isAdmin = true;
      }
      if (data?.data?.name) {
        this.username = data.data.name;
      }
    });
  }
}
