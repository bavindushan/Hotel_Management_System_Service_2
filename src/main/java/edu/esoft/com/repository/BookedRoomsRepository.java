package edu.esoft.com.repository;

import edu.esoft.com.entity.BookedRooms;
import edu.esoft.com.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookedRoomsRepository extends JpaRepository<BookedRooms, Integer> {

    List<BookedRooms> findByReservation(Reservation reservation);

}
