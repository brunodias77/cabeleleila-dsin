import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const isLoggedIn = !!localStorage.getItem('token'); // Verifica se há um token no localStorage
  if (isLoggedIn) {
    const router = inject(Router);
    router.navigate(['/profile']);
    return false;
  }
  return true;
};
