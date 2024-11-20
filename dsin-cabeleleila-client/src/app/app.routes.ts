import { Routes } from '@angular/router';
import { HomeComponent } from './modules/user/home/home.component';
import { LoginComponent } from './modules/authentication/login/login.component';
import { RegisterComponent } from './modules/authentication/register/register.component';
import { ProfileComponent } from './modules/user/profile/profile.component';
import { authGuard } from './guards/auth.guard';
import { PainelComponent } from './modules/admin/painel/painel.component';
import { WeeklyPerformanceComponent } from './modules/admin/painel/weekly-performance/weekly-performance.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent, canActivate: [authGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [authGuard] },
  { path: 'profile', component: ProfileComponent },
  { path: 'painel', component: PainelComponent },
  { path: 'painel/admin/performance', component: WeeklyPerformanceComponent },
  { path: 'painel/admin/create-service', component: PainelComponent },
  { path: 'painel/admin/appointments', component: PainelComponent },
];
