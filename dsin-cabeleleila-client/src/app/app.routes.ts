import { Routes } from '@angular/router';
import { HomeComponent } from './modules/user/home/home.component';
import { LoginComponent } from './modules/authentication/login/login.component';
import { RegisterComponent } from './modules/authentication/register/register.component';
import { ProfileComponent } from './modules/user/profile/profile.component';
import { authGuard } from './guards/auth.guard';
import { PainelComponent } from './modules/admin/painel/painel.component';
import { WeeklyPerformanceComponent } from './modules/admin/painel/weekly-performance/weekly-performance.component';
import { AdminAuthGuard } from './guards/admin-auth.guard';
import { ProfileGuard } from './guards/user-auth.guard';
import { UnauthorizedComponent } from './modules/user/unauthorized/unauthorized.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent, canActivate: [authGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [authGuard] },
  {
    path: 'profile',
    component: ProfileComponent,
  },
  { path: 'painel', component: PainelComponent, canActivate: [AdminAuthGuard] },
  { path: 'unauthorized', component: UnauthorizedComponent },
];
