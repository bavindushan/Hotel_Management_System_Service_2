package edu.esoft.com.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReservationRequestDTO {
    private Integer branchId;
    private Integer customerId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfOccupants;
    private Integer numberOfRooms;
    private List<Integer> roomIds;
}
