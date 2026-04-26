import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

interface Brand {
  id: number;
  name: string;
}

interface CarModel {
  id: number;
  name: string;
}

@Component({
  selector: 'app-estimation',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './estimation.html',
  styleUrl: './estimation.scss'
})
export class EstimationComponent implements OnInit {

  // Step management
  currentStep = 1;
  totalSteps = 3;

  // Data
  brands: Brand[] = [];
  models: CarModel[] = [];

  // Form
  selectedBrandId: number | null = null;
  selectedModelId: number | null = null;
  year: number | null = null;
  mileage: number | null = null;
  fuelType = '';
  transmission = '';
  condition = '';
  description = '';
  intention = '';

  loading = false;
  error = '';
  success = false;

  private readonly API = 'http://localhost:8080/api';

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.http.get<Brand[]>(`${this.API}/brands`).subscribe({
      next: (brands) => this.brands = brands,
      error: () => this.error = 'Erreur lors du chargement des marques'
    });
  }

  onBrandChange(): void {
    if (this.selectedBrandId) {
      this.selectedModelId = null;
      this.http.get<CarModel[]>(`${this.API}/car-models/brand/${this.selectedBrandId}`).subscribe({
        next: (models) => this.models = models
      });
    }
  }

  nextStep(): void {
    if (this.currentStep < this.totalSteps) {
      this.currentStep++;
    }
  }

  prevStep(): void {
    if (this.currentStep > 1) {
      this.currentStep--;
    }
  }

  isStep1Valid(): boolean {
    return !!this.selectedBrandId && !!this.selectedModelId &&
      !!this.year && !!this.mileage;
  }

  isStep2Valid(): boolean {
    return !!this.fuelType && !!this.transmission && !!this.condition;
  }

  onSubmit(): void {
    this.loading = true;
    this.error = '';

    const payload = {
      brandId: this.selectedBrandId,
      carModelId: this.selectedModelId,
      year: this.year,
      mileage: this.mileage,
      fuelType: this.fuelType,
      transmission: this.transmission,
      condition: this.condition,
      description: this.description,
      intention: this.intention
    };

    this.http.post(`${this.API}/estimations`, payload).subscribe({
      next: () => {
        this.success = true;
        this.loading = false;
      },
      error: () => {
        this.error = 'Une erreur est survenue, veuillez réessayer';
        this.loading = false;
      }
    });
  }
}
