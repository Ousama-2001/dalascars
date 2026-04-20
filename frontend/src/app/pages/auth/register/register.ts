import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class RegisterComponent {

  firstName = '';
  lastName = '';
  email = '';
  password = '';
  phone = '';
  city = '';
  error = '';
  loading = false;

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    this.loading = true;
    this.error = '';

    this.authService.register({
      firstName: this.firstName,
      lastName: this.lastName,
      email: this.email,
      password: this.password,
      phone: this.phone,
      city: this.city
    }).subscribe({
      next: () => {
        this.router.navigate(['/home']);
      },
      error: () => {
        this.error = 'Une erreur est survenue, veuillez réessayer';
        this.loading = false;
      }
    });
  }
}
