import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  private readonly TOKEN_KEY = 'dalascars_token';

  save(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  get(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  remove(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  isLoggedIn(): boolean {
    return !!this.get();
  }

  getRole(): string | null {
    const token = this.get();
    if (!token) return null;
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.role || null;
    } catch {
      return null;
    }
  }

  isAdmin(): boolean {
    return this.getRole() === 'ROLE_ADMIN';
  }
}
