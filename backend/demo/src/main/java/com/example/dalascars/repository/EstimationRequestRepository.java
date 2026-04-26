package com.example.dalascars.repository;

import com.example.dalascars.entity.EstimationRequest;
import com.example.dalascars.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EstimationRequestRepository extends JpaRepository<EstimationRequest, Long> {
    List<EstimationRequest> findByUserId(Long userId);
    List<EstimationRequest> findByStatus(RequestStatus status);
}