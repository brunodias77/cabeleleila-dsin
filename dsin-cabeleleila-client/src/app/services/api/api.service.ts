import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';

export interface RequestCreateAppointment {
  serviceId: string[];
  appointmentDate: string;
}

export interface BaseResponseDTO {
  status: number;
  message: string;
}

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
  }) {
    console.log('Aqui no services');
    console.log(registerData);
    return this.http.post(
      `${ApiService.API_URL}/auth/register-user`,
      registerData
    );
  }
  loginUser(loginData: { username: string; password: string }) {
    return this.http.post(`${ApiService.API_URL}/auth/login`, loginData);
  }

  getDataServices(): Observable<any> {
    return this.http.get(`${ApiService.API_URL}/service/all`);
  }

  getAllAppointments(): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
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
      return new Observable((observer) => {
        observer.next({});
        observer.complete();
      });
    }
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
    return this.http.get(`${ApiService.API_URL}/user`, { headers });
  }

  cancelAppointment(appointmentId: string): Observable<BaseResponseDTO> {
    const url = `${ApiService.API_URL}/appointment/cancel/${appointmentId}`;
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.put<BaseResponseDTO>(url, null, { headers });
  }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
  }
}
