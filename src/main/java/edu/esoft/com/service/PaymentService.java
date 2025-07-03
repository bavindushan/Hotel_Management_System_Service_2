package edu.esoft.com.service;

import edu.esoft.com.dto.PaymentDetailsRequestDTO;
import edu.esoft.com.dto.PaymentDetailsResponseDTO;

public interface PaymentService {
    PaymentDetailsResponseDTO addOrUpdate(Integer reservationId,
                                          PaymentDetailsRequestDTO dto);
}
