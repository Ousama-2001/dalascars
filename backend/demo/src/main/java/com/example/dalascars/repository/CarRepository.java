package com.example.dalascars.repository;

import com.example.dalascars.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findBySellerId(Long sellerId);
}