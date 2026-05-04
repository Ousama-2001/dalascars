package com.example.dalascars.service;

import com.example.dalascars.dto.AdminOfferDTO;
import com.example.dalascars.dto.EstimationRequestDTO;
import com.example.dalascars.entity.*;
import com.example.dalascars.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimationService {

    private final EstimationRequestRepository estimationRequestRepository;
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final CarModelRepository carModelRepository;

    // USER connecté OU anonyme — soumettre une demande
    public EstimationRequest submit(EstimationRequestDTO dto, String email) {

        User user = null;
        if (email != null) {
            user = userRepository.findByEmail(email).orElse(null);
        }

        Brand brand = null;
        if (dto.getBrandId() != null) {
            brand = brandRepository.findById(dto.getBrandId()).orElse(null);
        }

        CarModel carModel = null;
        if (dto.getCarModelId() != null) {
            carModel = carModelRepository.findById(dto.getCarModelId()).orElse(null);
        }

        EstimationRequest request = EstimationRequest.builder()
                .user(user)
                .contactFirstName(dto.getContactFirstName())
                .contactLastName(dto.getContactLastName())
                .contactEmail(dto.getContactEmail())
                .contactPhone(dto.getContactPhone())
                .brand(brand)
                .customBrand(dto.getCustomBrand())
                .carModel(carModel)
                .customModel(dto.getCustomModel())
                .year(dto.getYear())
                .mileage(dto.getMileage())
                .fuelType(dto.getFuelType())
                .transmission(dto.getTransmission())
                .condition(dto.getCondition())
                .numberOfDoors(dto.getNumberOfDoors())
                .technicalControl(dto.getTechnicalControl())
                .belgianVehicle(dto.getBelgianVehicle())
                .description(dto.getDescription())
                .intention(dto.getIntention())
                .build();

        return estimationRequestRepository.save(request);
    }

    // USER connecté — voir ses demandes
    public List<EstimationRequest> getMyRequests(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return estimationRequestRepository.findByUserId(user.getId());
    }

    // ADMIN — voir toutes les demandes
    public List<EstimationRequest> getAllRequests() {
        return estimationRequestRepository.findAll();
    }

    // ADMIN — faire une estimation / offre
    public EstimationRequest makeOffer(Long id, AdminOfferDTO dto) {
        EstimationRequest request = estimationRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setEstimatedPrice(dto.getEstimatedPrice());

        if (dto.getOfferPrice() != null) {
            request.setOfferPrice(dto.getOfferPrice());
            request.setStatus(RequestStatus.OFFRE_ENVOYEE);
        } else {
            request.setStatus(RequestStatus.ESTIME);
        }

        return estimationRequestRepository.save(request);
    }

    // USER — accepter ou refuser une offre
    public EstimationRequest respondToOffer(Long id, boolean accepted) {
        EstimationRequest request = estimationRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus(accepted ? RequestStatus.ACCEPTEE : RequestStatus.REFUSEE);
        return estimationRequestRepository.save(request);
    }
}