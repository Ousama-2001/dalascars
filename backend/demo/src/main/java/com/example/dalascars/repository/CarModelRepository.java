package com.example.dalascars.repository;

import com.example.dalascars.entity.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    List<CarModel> findByBrandId(Long brandId);
}