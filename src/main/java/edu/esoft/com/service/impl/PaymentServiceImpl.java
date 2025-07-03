package edu.esoft.com.service.impl;

import edu.esoft.com.dto.*;
import edu.esoft.com.entity.*;
import edu.esoft.com.enums.PaymentStatus;
import edu.esoft.com.enums.ReservationStatus;
import edu.esoft.com.repository.*;
import edu.esoft.com.service.PaymentService;
import edu.esoft.com.util.CardMaskUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final ReservationRepository               reservationRepo;
    private final ReservationPaymentDetailsRepository detailsRepo;
    private final ModelMapper                         mapper;

    @Override
    public PaymentDetailsResponseDTO addOrUpdate(Integer reservationId,
                                                 PaymentDetailsRequestDTO dto) {

        Reservation reservation = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        ReservationPaymentDetails details =
                detailsRepo.findByReservation(reservation)
                        .orElse(new ReservationPaymentDetails());

        details.setReservation(reservation);
        details.setCardType(dto.getCardType());
        details.setCardNumber(dto.getCardNumber());
        details.setCardExpMonth(dto.getCardExpMonth());
        details.setCardExpYear(dto.getCardExpYear());
        details.setCvnCode(dto.getCvnCode());

        detailsRepo.save(details);

        // mark reservation CONFIRMED
        reservation.setPaymentStatus(PaymentStatus.Confirmed);
        //reservation.setReservationStatus(ReservationStatus.Confirmed);
        reservationRepo.save(reservation);

        // build response
        PaymentDetailsResponseDTO resp = new PaymentDetailsResponseDTO();
        resp.setReservationId(reservationId);
        resp.setCardType(details.getCardType());
        resp.setCardExpMonth(details.getCardExpMonth());
        resp.setCardExpYear(details.getCardExpYear());
        resp.setMaskedCardNumber(CardMaskUtil.mask(details.getCardNumber()));
        return resp;
    }
}