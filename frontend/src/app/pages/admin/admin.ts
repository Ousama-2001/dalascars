import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

interface EstimationRequest {
  id: number;
  user: { id: number; firstName: string; lastName: string; email: string; phone: string; city: string } | null;
  contactFirstName: string | null;
  contactLastName: string | null;
  contactEmail: string | null;
  contactPhone: string | null;
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
  description: string;
  intention: string;
  status: string;
  estimatedPrice: number | null;
  offerPrice: number | null;
  createdAt: string;
}

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './admin.html',
  styleUrl: './admin.scss'
})
export class AdminComponent implements OnInit {

  requests: EstimationRequest[] = [];
  loading = true;
  error = '';
  selectedRequest: EstimationRequest | null = null;
  estimatedPrice: number | null = null;
  offerPrice: number | null = null;
  submitting = false;

  private readonly API = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadRequests();
  }

  loadRequests(): void {
    this.http.get<EstimationRequest[]>(`${this.API}/estimations/admin`).subscribe({
      next: (data) => {
        this.requests = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Erreur lors du chargement';
        this.loading = false;
      }
    });
  }

  selectRequest(req: EstimationRequest): void {
    this.selectedRequest = req;
    this.estimatedPrice = req.estimatedPrice;
    this.offerPrice = req.offerPrice;
  }

  closePanel(): void {
    this.selectedRequest = null;
    this.estimatedPrice = null;
    this.offerPrice = null;
  }

  submitOffer(): void {
    if (!this.selectedRequest) return;
    this.submitting = true;

    const payload = {
      estimatedPrice: this.estimatedPrice,
      offerPrice: this.offerPrice
    };

    this.http.patch(`${this.API}/estimations/admin/${this.selectedRequest.id}/offer`, payload).subscribe({
      next: () => {
        this.submitting = false;
        this.closePanel();
        this.loadRequests();
      },
      error: () => {
        this.error = 'Erreur lors de la soumission';
        this.submitting = false;
      }
    });
  }

  getContactName(req: EstimationRequest): string {
    if (req.user) return req.user.firstName + ' ' + req.user.lastName;
    return (req.contactFirstName || '') + ' ' + (req.contactLastName || '');
  }

  getContactEmail(req: EstimationRequest): string {
    if (req.user) return req.user.email;
    return req.contactEmail || '';
  }

  getContactPhone(req: EstimationRequest): string {
    if (req.user) return req.user.phone || '';
    return req.contactPhone || '';
  }

  getBrandName(req: EstimationRequest): string {
    return req.brand ? req.brand.name : (req.customBrand || '');
  }

  getModelName(req: EstimationRequest): string {
    return req.carModel ? req.carModel.name : (req.customModel || '');
  }

  getStatusLabel(status: string): string {
    switch(status) {
      case 'EN_ATTENTE': return 'En attente';
      case 'ESTIME': return 'Estimé';
      case 'OFFRE_ENVOYEE': return 'Offre envoyée';
      case 'ACCEPTEE': return 'Acceptée';
      case 'REFUSEE': return 'Refusée';
      default: return status;
    }
  }

  getStatusColor(status: string): string {
    switch(status) {
      case 'EN_ATTENTE': return '#f59e0b';
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

  getTechnicalControlLabel(tc: string | null): string {
    switch(tc) {
      case 'VALIDE': return '✅ Valide';
      case 'EXPIRE': return '❌ Expiré';
      case 'A_PASSER': return '⏳ À passer';
      default: return '—';
    }
  }

  formatPrice(price: number): string {
    return new Intl.NumberFormat('fr-BE', { style: 'currency', currency: 'EUR' }).format(price);
  }

  formatDate(date: string): string {
    return new Date(date).toLocaleDateString('fr-BE', { day: '2-digit', month: 'long', year: 'numeric' });
  }

  get pendingCount(): number {
    return this.requests.filter(r => r.status === 'EN_ATTENTE').length;
  }

  get estimatedCount(): number {
    return this.requests.filter(r => r.status === 'ESTIME').length;
  }

  get offerCount(): number {
    return this.requests.filter(r => r.status === 'OFFRE_ENVOYEE').length;
  }

  get acceptedCount(): number {
    return this.requests.filter(r => r.status === 'ACCEPTEE').length;
  }
}
