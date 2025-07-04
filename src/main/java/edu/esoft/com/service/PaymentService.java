package edu.esoft.com.service;

import edu.esoft.com.dto.PaymentDetailsRequestDTO;
import edu.esoft.com.dto.PaymentDetailsResponseDTO;
import edu.esoft.com.dto.PaymentRequestDTO;
import edu.esoft.com.dto.PaymentResponseDTO;

public interface PaymentService {
    PaymentDetailsResponseDTO addOrUpdate(Integer reservationId,
                                          PaymentDetailsRequestDTO dto);
    PaymentResponseDTO makeCashPayment(Integer reservationId,
                                       PaymentRequestDTO dto);

    PaymentResponseDTO getByReservation(Integer reservationId);
}
