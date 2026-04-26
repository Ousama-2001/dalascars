package com.example.dalascars.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminOfferDTO {
    private Double estimatedPrice;
    private Double offerPrice;
}