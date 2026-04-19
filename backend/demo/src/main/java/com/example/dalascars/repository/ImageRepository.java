package com.example.dalascars.repository;

import com.example.dalascars.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByCarId(Long carId);
}