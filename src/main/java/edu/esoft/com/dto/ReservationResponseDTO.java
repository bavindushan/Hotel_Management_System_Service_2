package edu.esoft.com.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReservationResponseDTO {
    private Integer reservationId;
    private Integer customerId;
    private Integer branchId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String reservationStatus;
    private String paymentStatus;
    private List<Integer> bookedRoomIds;
}
