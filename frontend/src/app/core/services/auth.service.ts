import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { TokenService } from './token.service';

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  phone?: string;
  city?: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  email: string;
  firstName: string;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly API = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient, private tokenService: TokenService) {}

  register(data: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.API}/register`, data).pipe(
      tap(res => this.tokenService.save(res.token))
    );
  }

  login(data: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.API}/login`, data).pipe(
      tap(res => this.tokenService.save(res.token))
    );
  }

  logout(): void {
    this.tokenService.remove();
  }

  isLoggedIn(): boolean {
    return this.tokenService.isLoggedIn();
  }
}
