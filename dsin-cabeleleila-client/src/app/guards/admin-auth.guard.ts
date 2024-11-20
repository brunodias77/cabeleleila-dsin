import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AdminAuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    const role = localStorage.getItem('role'); // Obtém o valor da role no localStorage

    // Verifica se a role é "ROLE_ADMIN"
    if (role === 'ROLE_ADMIN') {
      return true; // Permite o acesso à rota
    }

    // Caso contrário, redireciona para uma página de erro ou login
    this.router.navigate(['/unauthorized']);
    return false;
  }
}
