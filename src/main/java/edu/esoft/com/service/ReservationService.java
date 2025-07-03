package edu.esoft.com.service;

import edu.esoft.com.dto.ReservationRequestDTO;
import edu.esoft.com.dto.ReservationResponseDTO;
import edu.esoft.com.enums.ReservationStatus;

import java.util.List;

public interface ReservationService {

    ReservationResponseDTO createReservation(ReservationRequestDTO request);
    void cancelReservation(Integer reservationId);
    void updateReservationStatus(Integer reservationId, ReservationStatus newStatus);
    List<ReservationResponseDTO> getAllReservations();
    List<ReservationResponseDTO> getReservationsByCustomer(Integer customerId);
}
