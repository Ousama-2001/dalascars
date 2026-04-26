package com.example.dalascars.controller;

import com.example.dalascars.entity.CarModel;
import com.example.dalascars.repository.CarModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car-models")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CarModelController {

    private final CarModelRepository carModelRepository;

    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<CarModel>> getByBrand(@PathVariable Long brandId) {
        return ResponseEntity.ok(carModelRepository.findByBrandId(brandId));
    }
}