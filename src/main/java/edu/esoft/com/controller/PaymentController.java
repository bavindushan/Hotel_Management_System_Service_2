package edu.esoft.com.controller;

import edu.esoft.com.dto.*;
import edu.esoft.com.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}