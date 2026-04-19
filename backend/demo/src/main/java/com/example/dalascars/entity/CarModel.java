package com.example.dalascars.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "car_models")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @OneToMany(mappedBy = "carModel", cascade = CascadeType.ALL)
    private List<Car> cars;
}