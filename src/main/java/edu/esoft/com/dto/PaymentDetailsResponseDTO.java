package edu.esoft.com.dto;

import lombok.Data;

@Data
public class PaymentDetailsResponseDTO {
    private Integer reservationId;
    private String  cardType;
    private String  maskedCardNumber;
    private String  cardExpMonth;
    private String  cardExpYear;
}
