import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

interface UserState {
  email: string | null;
  role: string | null;
}

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private userSubject = new BehaviorSubject<UserState>({
    email: null,
    role: null,
  });

  public user$ = this.userSubject.asObservable();

  constructor() {
    const email = localStorage.getItem('email');
    const role = localStorage.getItem('role');
    if (email && role) {
      this.userSubject.next({ email, role });
    }
  }

  setUser(email: string, role: string): void {
    console.log('Atualizando estado do usuário:', { email, role });
    this.userSubject.next({ email, role });
  }

  clearUser(): void {
    console.log('Limpando estado do usuário');
    this.userSubject.next({ email: null, role: null });
  }

  getRole(): string | null {
    return this.userSubject.value.role;
  }
}
