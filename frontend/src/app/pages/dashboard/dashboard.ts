import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

interface EstimationRequest {
  id: number;
  brand: { id: number; name: string };
  carModel: { id: number; name: string };
  year: number;
  mileage: number;
  fuelType: string;
  transmission: string;
  condition: string;
  description: string;
  intention: string;
  status: string;
  estimatedPrice: number | null;
  offerPrice: number | null;
  createdAt: string;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss'
})
export class DashboardComponent implements OnInit {

  requests: EstimationRequest[] = [];
  loading = true;
  error = '';

  private readonly API = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadRequests();
  }

  loadRequests(): void {
    this.http.get<EstimationRequest[]>(`${this.API}/estimations/my`).subscribe({
      next: (data) => {
        this.requests = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Erreur lors du chargement de vos demandes';
        this.loading = false;
      }
    });
  }

  respondToOffer(id: number, accepted: boolean): void {
    this.http.patch(`${this.API}/estimations/${id}/respond?accepted=${accepted}`, {}).subscribe({
      next: () => this.loadRequests(),
      error: () => this.error = 'Erreur lors de la réponse'
    });
  }

  getStatusLabel(status: string): string {
    switch(status) {
      case 'EN_ATTENTE': return 'En attente';
      case 'ESTIME': return 'Estimé';
      case 'OFFRE_ENVOYEE': return 'Offre reçue';
      case 'ACCEPTEE': return 'Acceptée';
      case 'REFUSEE': return 'Refusée';
      default: return status;
    }
  }

  getStatusColor(status: string): string {
    switch(status) {
      case 'EN_ATTENTE': return 'rgba(255,255,255,0.2)';
      case 'ESTIME': return '#3B82F6';
      case 'OFFRE_ENVOYEE': return '#D4AF37';
      case 'ACCEPTEE': return '#22c55e';
      case 'REFUSEE': return '#ef4444';
      default: return 'rgba(255,255,255,0.2)';
    }
  }

  getFuelLabel(fuel: string): string {
    switch(fuel) {
      case 'GASOLINE': return 'Essence';
      case 'DIESEL': return 'Diesel';
      case 'ELECTRIC': return 'Électrique';
      case 'HYBRID': return 'Hybride';
      default: return fuel;
    }
  }

  getIntentionLabel(intention: string): string {
    return intention === 'SELL' ? 'Estimer & Vendre' : 'Estimation uniquement';
  }

  formatPrice(price: number): string {
    return new Intl.NumberFormat('fr-BE', { style: 'currency', currency: 'EUR' }).format(price);
  }

  formatDate(date: string): string {
    return new Date(date).toLocaleDateString('fr-BE', { day: '2-digit', month: 'long', year: 'numeric' });
  }
}
