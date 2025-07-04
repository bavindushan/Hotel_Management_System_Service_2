// src/main/java/edu/esoft/com/dto/PaymentResponseDTO.java
package edu.esoft.com.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentResponseDTO {
    private Integer paymentId;
    private Integer reservationId;
    private Double  amountPaid;
    private String  paidAt;
    private String  paymentStatus;   // "PAID"
}
