package edu.esoft.com.controller;

import edu.esoft.com.dto.*;
import edu.esoft.com.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PutMapping("/{id}/payment-details")
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    public ResponseEntity<PaymentDetailsResponseDTO> savePayment(
            @PathVariable Integer id,
            @RequestBody @Valid PaymentDetailsRequestDTO body) {

        PaymentDetailsResponseDTO resp = paymentService.addOrUpdate(id, body);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/reservation/{id}/cash")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    public PaymentResponseDTO payCash(@PathVariable("id") Integer reservationId,
                                      @Valid @RequestBody PaymentRequestDTO dto) {
        return paymentService.makeCashPayment(reservationId, dto);
    }

    @GetMapping("/reservation/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    public PaymentResponseDTO getPayment(@PathVariable("id") Integer reservationId) {
        return paymentService.getByReservation(reservationId);
    }
}