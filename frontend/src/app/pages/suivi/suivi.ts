import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';

interface EstimationRequest {
  id: number;
  brand: { id: number; name: string } | null;
  carModel: { id: number; name: string } | null;
  customBrand: string | null;
  customModel: string | null;
  year: number;
  mileage: number;
  fuelType: string;
  transmission: string;
  condition: string;
  numberOfDoors: number | null;
  technicalControl: string | null;
  belgianVehicle: boolean | null;
  description: string | null;
  intention: string;
  status: string;
  estimatedPrice: number | null;
  offerPrice: number | null;
  contactFirstName: string | null;
  contactLastName: string | null;
  createdAt: string;
}

@Component({
  selector: 'app-suivi',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './suivi.html',
  styleUrl: './suivi.scss'
})
export class SuiviComponent implements OnInit {

  request: EstimationRequest | null = null;
  loading = true;
  error = '';

  private readonly API = 'http://localhost:8080/api';

  constructor(private http: HttpClient, private route: ActivatedRoute) {}

  ngOnInit(): void {
    const token = this.route.snapshot.paramMap.get('token');
    if (token) {
      this.http.get<EstimationRequest>(`${this.API}/estimations/track/${token}`).subscribe({
        next: (data) => {
          this.request = data;
          this.loading = false;
        },
        error: () => {
          this.error = 'Demande introuvable ou lien invalide.';
          this.loading = false;
        }
      });
    }
  }

  getBrandName(): string {
    if (!this.request) return '';
    return this.request.brand ? this.request.brand.name : (this.request.customBrand || '');
  }

  getModelName(): string {
    if (!this.request) return '';
    return this.request.carModel ? this.request.carModel.name : (this.request.customModel || '');
  }

  getStatusLabel(): string {
    switch(this.request?.status) {
      case 'EN_ATTENTE': return 'En attente d\'analyse';
      case 'ESTIME': return 'Estimation reçue';
      case 'OFFRE_ENVOYEE': return 'Offre d\'achat reçue';
      case 'ACCEPTEE': return 'Offre acceptée';
      case 'REFUSEE': return 'Offre refusée';
      default: return '';
    }
  }

  getStatusColor(): string {
    switch(this.request?.status) {
      case 'EN_ATTENTE': return '#f59e0b';
      case 'ESTIME': return '#3B82F6';
      case 'OFFRE_ENVOYEE': return '#D4AF37';
      case 'ACCEPTEE': return '#22c55e';
      case 'REFUSEE': return '#ef4444';
      default: return 'rgba(255,255,255,0.2)';
    }
  }

  getFuelLabel(): string {
    switch(this.request?.fuelType) {
      case 'GASOLINE': return 'Essence';
      case 'DIESEL': return 'Diesel';
      case 'ELECTRIC': return 'Électrique';
      case 'HYBRID': return 'Hybride';
      default: return '';
    }
  }

  formatPrice(price: number): string {
    return new Intl.NumberFormat('fr-BE', { style: 'currency', currency: 'EUR' }).format(price);
  }

  formatDate(date: string): string {
    return new Date(date).toLocaleDateString('fr-BE', { day: '2-digit', month: 'long', year: 'numeric' });
  }
}
