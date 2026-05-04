package com.example.dalascars.controller;

import com.example.dalascars.dto.AdminOfferDTO;
import com.example.dalascars.dto.EstimationRequestDTO;
import com.example.dalascars.entity.EstimationRequest;
import com.example.dalascars.service.EstimationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/estimations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EstimationController {

    private final EstimationService estimationService;

    // PUBLIC — soumettre une demande
    @PostMapping
    public ResponseEntity<EstimationRequest> submit(
            @Valid @RequestBody EstimationRequestDTO dto,
            Principal principal) {
        String email = principal != null ? principal.getName() : null;
        return ResponseEntity.ok(estimationService.submit(dto, email));
    }

    // PUBLIC — suivre une demande par token
    @GetMapping("/track/{token}")
    public ResponseEntity<EstimationRequest> track(@PathVariable String token) {
        return estimationService.getByToken(token)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // USER connecté — voir ses demandes
    @GetMapping("/my")
    public ResponseEntity<List<EstimationRequest>> getMyRequests(Principal principal) {
        return ResponseEntity.ok(estimationService.getMyRequests(principal.getName()));
    }

    // USER — accepter ou refuser une offre
    @PatchMapping("/{id}/respond")
    public ResponseEntity<EstimationRequest> respond(
            @PathVariable Long id,
            @RequestParam boolean accepted) {
        return ResponseEntity.ok(estimationService.respondToOffer(id, accepted));
    }

    // ADMIN — voir toutes les demandes
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EstimationRequest>> getAllRequests() {
        return ResponseEntity.ok(estimationService.getAllRequests());
    }

    // ADMIN — faire une estimation / offre
    @PatchMapping("/admin/{id}/offer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstimationRequest> makeOffer(
            @PathVariable Long id,
            @RequestBody AdminOfferDTO dto) {
        return ResponseEntity.ok(estimationService.makeOffer(id, dto));
    }
}