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

  currentStep = 1;
  totalSteps = 4;

  brands: Brand[] = [];
  models: CarModel[] = [];

  // Step 1
  selectedBrandId: number | null = null;
  customBrand = '';
  selectedModelId: number | null = null;
  customModel = '';
  year: number | null = null;
  mileage: number | null = null;
  numberOfDoors: number | null = null;
  belgianVehicle: boolean | null = null;

  // Step 2
  fuelType = '';
  transmission = '';
  condition = '';
  technicalControl = '';

  // Step 3
  intention = '';
  description = '';

  // Step 4
  contactFirstName = '';
  contactLastName = '';
  contactEmail = '';
  contactPhone = '';

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
    this.selectedModelId = null;
    this.models = [];
    if (this.selectedBrandId && this.selectedBrandId !== -1) {
      this.http.get<CarModel[]>(`${this.API}/car-models/brand/${this.selectedBrandId}`).subscribe({
        next: (models) => this.models = models
      });
    }
  }

  nextStep(): void {
    if (this.currentStep < this.totalSteps) this.currentStep++;
  }

  prevStep(): void {
    if (this.currentStep > 1) this.currentStep--;
  }

  isStep1Valid(): boolean {
    const brandOk = this.selectedBrandId === -1 ? !!this.customBrand : !!this.selectedBrandId;
    const modelOk = this.selectedModelId === -1 ? !!this.customModel : !!this.selectedModelId;
    return brandOk && modelOk && !!this.year && !!this.mileage &&
      !!this.numberOfDoors && this.belgianVehicle !== null;
  }

  isStep2Valid(): boolean {
    return !!this.fuelType && !!this.transmission && !!this.condition && !!this.technicalControl;
  }

  isStep3Valid(): boolean {
    return !!this.intention;
  }

  isStep4Valid(): boolean {
    return !!this.contactFirstName && !!this.contactLastName &&
      !!this.contactEmail && !!this.contactPhone;
  }

  onSubmit(): void {
    this.loading = true;
    this.error = '';

    const payload = {
      brandId: this.selectedBrandId === -1 ? null : this.selectedBrandId,
      customBrand: this.selectedBrandId === -1 ? this.customBrand : null,
      carModelId: this.selectedModelId === -1 ? null : this.selectedModelId,
      customModel: this.selectedModelId === -1 ? this.customModel : null,
      year: this.year,
      mileage: this.mileage,
      numberOfDoors: this.numberOfDoors,
      belgianVehicle: this.belgianVehicle,
      fuelType: this.fuelType,
      transmission: this.transmission,
      condition: this.condition,
      technicalControl: this.technicalControl,
      description: this.description,
      intention: this.intention,
      contactFirstName: this.contactFirstName,
      contactLastName: this.contactLastName,
      contactEmail: this.contactEmail,
      contactPhone: this.contactPhone
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
