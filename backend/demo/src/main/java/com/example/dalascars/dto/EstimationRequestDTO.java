package com.example.dalascars.dto;

import com.example.dalascars.entity.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstimationRequestDTO {

    // Contact (si pas connecté)
    private String contactFirstName;
    private String contactLastName;

    @Email
    private String contactEmail;

    private String contactPhone;

    // Marque — soit ID soit texte libre
    private Long brandId;
    private String customBrand;

    // Modèle — soit ID soit texte libre
    private Long carModelId;
    private String customModel;

    @NotNull
    private int year;

    @NotNull
    private int mileage;

    @NotNull
    private FuelType fuelType;

    @NotNull
    private Transmission transmission;

    @NotNull
    private CarCondition condition;

    private Integer numberOfDoors;

    private TechnicalControl technicalControl;

    private Boolean belgianVehicle;

    private String description;

    @NotNull
    private Intention intention;
}