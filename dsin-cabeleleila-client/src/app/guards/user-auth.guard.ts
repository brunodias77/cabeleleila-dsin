import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class ProfileGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    const role = localStorage.getItem('role');

    // Verifica se a role está ausente ou é "ROLE_ADMIN"
    if (!role || role === 'ROLE_ADMIN') {
      this.router.navigate(['/unauthorized']); // Redireciona para a página não autorizada
      return false;
    }

    return true; // Permite acesso se não for "ROLE_ADMIN"
  }
}
