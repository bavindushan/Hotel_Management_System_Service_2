package edu.esoft.com.repository;

import edu.esoft.com.entity.Reservation;
import edu.esoft.com.entity.ReservationPaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationPaymentDetailsRepository
        extends JpaRepository<ReservationPaymentDetails, Integer> {

    Optional<ReservationPaymentDetails> findByReservation(Reservation reservation);
}
