package com.example.dalascars.dto;

import com.example.dalascars.entity.CarCondition;
import com.example.dalascars.entity.FuelType;
import com.example.dalascars.entity.Intention;
import com.example.dalascars.entity.Transmission;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstimationRequestDTO {

    @NotNull
    private Long brandId;

    @NotNull
    private Long carModelId;

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

    private String description;

    @NotNull
    private Intention intention;
}