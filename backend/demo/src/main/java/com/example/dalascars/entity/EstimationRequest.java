package com.example.dalascars.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "estimation_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstimationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User optionnel (null si pas connecté)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Infos contact si pas connecté
    @Column
    private String contactFirstName;

    @Column
    private String contactLastName;

    @Column
    private String contactEmail;

    @Column
    private String contactPhone;

    // Marque — soit depuis la DB soit saisie manuelle
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column
    private String customBrand;

    // Modèle — soit depuis la DB soit saisie manuelle
    @ManyToOne
    @JoinColumn(name = "car_model_id")
    private CarModel carModel;

    @Column
    private String customModel;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int mileage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Transmission transmission;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarCondition condition;

    @Column
    private Integer numberOfDoors;

    @Enumerated(EnumType.STRING)
    @Column
    private TechnicalControl technicalControl;

    @Column
    private Boolean belgianVehicle;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Intention intention;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    private Double estimatedPrice;
    private Double offerPrice;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.status = RequestStatus.EN_ATTENTE;
    }
}