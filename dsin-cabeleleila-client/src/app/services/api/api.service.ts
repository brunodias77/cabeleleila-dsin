import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, throwError } from 'rxjs';
import {
  Appointment,
  BaseResponseDTO,
  RequestCreateAppointment,
  RequestUpadateAppointment,
} from '../../types';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private static readonly API_URL = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  registerUser(registerData: {
    name: string;
    email: string;
    password: string;
    phoneNumber: string;
  }): Observable<any> {
    return this.http.post(
      `${ApiService.API_URL}/auth/register-user`,
      registerData
    );
  }

  loginUser(loginData: {
    username: string;
    password: string;
  }): Observable<any> {
    return this.http.post(`${ApiService.API_URL}/auth/login`, loginData);
  }

  getDataServices(): Observable<any> {
    return this.http.get(`${ApiService.API_URL}/service/all`);
  }

  getAllAppointments(): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get(`${ApiService.API_URL}/appointment/all`, { headers });
  }

  createAppointment(
    request: RequestCreateAppointment
  ): Observable<BaseResponseDTO> {
    const url = `${ApiService.API_URL}/appointment/create`;
    const headers = this.getAuthHeaders();
    return this.http.post<BaseResponseDTO>(url, request, { headers });
  }

  getUserDetails(): Observable<any> {
    const token = localStorage.getItem('token');
    if (!token) {
      return of({}); // Return an empty observable when no token is available
    }
    const headers = this.getAuthHeaders();
    return this.http.get(`${ApiService.API_URL}/user`, { headers });
  }

  cancelAppointment(appointmentId: string): Observable<BaseResponseDTO> {
    const url = `${ApiService.API_URL}/appointment/cancel/${appointmentId}`;
    const headers = this.getAuthHeaders();
    return this.http.put<BaseResponseDTO>(url, null, { headers });
  }

  updateAppointment(
    appointmentId: string,
    appointment: RequestUpadateAppointment
  ): Observable<BaseResponseDTO> {
    const url = `${ApiService.API_URL}/appointment/update/${appointmentId}`;
    const headers = this.getAuthHeaders();
    return this.http.put<BaseResponseDTO>(url, appointment, { headers }).pipe(
      catchError((error) => {
        console.error('Error updating appointment:', error);
        return throwError(() => new Error('Falha ao atualizar o compromisso.'));
      })
    );
  }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    if (!token) {
      console.warn('No token found in localStorage.');
    }
    return new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: token ? `Bearer ${token}` : '',
    });
  }

  getPerformaceData(): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get(`${ApiService.API_URL}/admin/performace`, {
      headers,
    });
  }
}
