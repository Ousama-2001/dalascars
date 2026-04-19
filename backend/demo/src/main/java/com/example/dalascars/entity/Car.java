package com.example.dalascars.entity;

import jakarta.persistence.*;
import lombok.*;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_model_id", nullable = false)
    private CarModel carModel;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User seller;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int mileage;

    @Column(nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private String city;

    @Enumerated(EnumType.STRING)
    private CarStatus status;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Image> images;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}