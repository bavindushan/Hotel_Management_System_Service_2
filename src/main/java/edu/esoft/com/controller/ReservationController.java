package edu.esoft.com.controller;

import edu.esoft.com.dto.ReservationRequestDTO;
import edu.esoft.com.dto.ReservationResponseDTO;
import edu.esoft.com.dto.ReservationStatusUpdateDTO;
import edu.esoft.com.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    public ResponseEntity<ReservationResponseDTO> createReservation(
            @Valid @RequestBody ReservationRequestDTO request) {

        ReservationResponseDTO response = reservationService.createReservation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{reservationId}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    public ResponseEntity<Void> cancelReservation(@PathVariable Integer reservationId) {
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    public ResponseEntity<Void> updateStatus(@PathVariable Integer id,
                                             @RequestBody @Valid ReservationStatusUpdateDTO dto) {

        reservationService.updateReservationStatus(id, dto.getNewStatus());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> getAll() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ReservationResponseDTO>> byCustomer(
            @PathVariable Integer customerId) {
        return ResponseEntity.ok(
                reservationService.getReservationsByCustomer(customerId));
    }
}
