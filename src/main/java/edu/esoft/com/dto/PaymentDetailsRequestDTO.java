package edu.esoft.com.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PaymentDetailsRequestDTO {
    @NotBlank
    private String cardType;

    @Pattern(regexp="\\d{13,19}")
    private String cardNumber;

    @Pattern(regexp="\\d{2}")
    private String cardExpMonth;

    @Pattern(regexp="\\d{4}")
    private String cardExpYear;

    @Pattern(regexp="\\d{3,4}")
    private String cvnCode;
}
