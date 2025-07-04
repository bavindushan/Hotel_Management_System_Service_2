package edu.esoft.com.service.impl;

import edu.esoft.com.dto.*;
import edu.esoft.com.entity.*;
import edu.esoft.com.enums.PaymentMethod;
import edu.esoft.com.enums.PaymentStatus;
import edu.esoft.com.enums.ReservationStatus;
import edu.esoft.com.enums.RoomStatus;
import edu.esoft.com.repository.*;
import edu.esoft.com.service.PaymentService;
import edu.esoft.com.util.CardMaskUtil;
import edu.esoft.com.util.PDFGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final ReservationRepository               reservationRepo;
    private final ReservationPaymentDetailsRepository detailsRepo;
    private final ModelMapper                         mapper;
    private final PaymentRepository     paymentRepo;
    private final RoomRepository roomRepo;

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

    @Override
    public PaymentResponseDTO makeCashPayment(Integer reservationId,
                                              PaymentRequestDTO dto) {

        //  Reservation must exist
        Reservation reservation = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        // check already paid
        paymentRepo.findByReservation_Id(reservationId).ifPresent(p -> {
            throw new RuntimeException("Reservation already paid");
        });

        //  Persist payment (method is always CASH)
        Payment payment = Payment.builder()
                .reservation(reservation)
                .paymentMethod(PaymentMethod.Cash)
                .amountPaid(dto.getAmountPaid())
                .build();

        paymentRepo.save(payment);

        //  Flip reservation → PAID / CONFIRMED
        reservation.setPaymentStatus(PaymentStatus.Paid);
        reservation.setReservationStatus(ReservationStatus.Complete);
        reservationRepo.save(reservation);

        //  Free all rooms reserved under this reservation
        List<BookedRooms> bookedRooms = reservation.getBookedRooms();
        for (BookedRooms br : bookedRooms) {
            Room room = br.getRoom();
            room.setStatus(RoomStatus.Available);
            roomRepo.save(room);
        }
        // Generate PDF bill
        byte[] pdfBytes = PDFGenerator.generateBillPDF(payment);

        return mapper.map(payment, PaymentResponseDTO.class);
    }

    @Override
    public PaymentResponseDTO getByReservation(Integer reservationId) {
        Payment payment = paymentRepo.findByReservation_Id(reservationId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return mapper.map(payment, PaymentResponseDTO.class);
    }
}