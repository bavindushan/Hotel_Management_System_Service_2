// src/main/java/edu/esoft/com/dto/PaymentRequestDTO.java
package edu.esoft.com.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentRequestDTO {
    @NotNull
    private Integer reservationId;

    @NotNull @DecimalMin("0.01")
    private Double amountPaid;
}
