package edu.esoft.com.service.impl;

import edu.esoft.com.dto.ReservationRequestDTO;
import edu.esoft.com.dto.ReservationResponseDTO;
import edu.esoft.com.entity.*;
import edu.esoft.com.enums.PaymentStatus;
import edu.esoft.com.enums.ReservationStatus;
import edu.esoft.com.enums.RoomStatus;
import edu.esoft.com.repository.*;
import edu.esoft.com.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepo;
    private final RoomRepository        roomRepo;
    private final CustomerRepository    customerRepo;
    private final BranchRepository      branchRepo;
    private final BookedRoomsRepository bookedRepo;
    private final ModelMapper           mapper;

    @Override
    public ReservationResponseDTO createReservation(ReservationRequestDTO req) {

        /* 1️ Validate linked entities */
        Customer customer = customerRepo.findById(req.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Branch branch = branchRepo.findById(req.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        /* 2️ Fetch all requested rooms in one go */
        List<Room> rooms = roomRepo.findAllById(req.getRoomIds());

        if (rooms.size() != req.getRoomIds().size()) {
            throw new RuntimeException("One or more rooms do not exist");
        }

        for (Room room : rooms) {
            if (room.getStatus() != RoomStatus.Available) {
                throw new RuntimeException("Room not available: " + room.getId());
            }
            if (!room.getBranch().getId().equals(branch.getId())) {
                throw new RuntimeException("Room " + room.getId() + " does not belong to branch " + branch.getId());
            }
        }

        /* 3️ Create reservation */
        Reservation reservation = Reservation.builder()
                .customer(customer)
                .branch(branch)
                .checkInDate(req.getCheckInDate())
                .checkOutDate(req.getCheckOutDate())
                .numberOfOccupants(req.getNumberOfOccupants())
                .numberOfRooms(rooms.size())             // derive, don’t trust client
                .reservationStatus(ReservationStatus.Confirmed)
                .paymentStatus(PaymentStatus.Pending)
                .build();

        reservationRepo.save(reservation);

        /* 4️ Persist BookedRooms + mark rooms occupied */
        List<BookedRooms> bookedEntities = new ArrayList<>();

        for (Room room : rooms) {
            room.setStatus(RoomStatus.Occupied);   // update status
            bookedEntities.add(
                    BookedRooms.builder()
                            .reservation(reservation)
                            .room(room)
                            .build());
        }

        roomRepo.saveAll(rooms);        // batch update
        bookedRepo.saveAll(bookedEntities);

        /* 5️ Map to response DTO */
        ReservationResponseDTO res = mapper.map(reservation, ReservationResponseDTO.class);
        res.setBookedRoomIds(
                rooms.stream().map(Room::getId).collect(Collectors.toList())
        );

        return res;
    }

    @Override
    public void cancelReservation(Integer reservationId) {

        Reservation reservation = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (reservation.getReservationStatus() == ReservationStatus.Cancelled) {
            throw new RuntimeException("Reservation is already cancelled.");
        }

        // Update reservation status
        reservation.setReservationStatus(ReservationStatus.Cancelled);
        reservationRepo.save(reservation);

        // Free all booked rooms
        List<BookedRooms> bookedRooms = bookedRepo.findByReservation(reservation);

        for (BookedRooms br : bookedRooms) {
            Room room = br.getRoom();
            room.setStatus(RoomStatus.Available);
            roomRepo.save(room);
        }
    }

    @Override
    public void updateReservationStatus(Integer reservationId, ReservationStatus newStatus) {

        Reservation reservation = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        ReservationStatus current = reservation.getReservationStatus();

        // Basic guards
        if (current == ReservationStatus.Cancelled) {
            throw new RuntimeException("Cannot update a cancelled reservation");
        }
        if (current == newStatus) return;                     // nothing to do

        // Apply side‑effects on rooms if needed
        if (newStatus == ReservationStatus.No_show
                || newStatus == ReservationStatus.Complete) {

            List<BookedRooms> booked = bookedRepo.findByReservation(reservation);

            for (BookedRooms br : booked) {
                Room room = br.getRoom();
                room.setStatus(RoomStatus.Available);
            }
            roomRepo.saveAll(                              // batch update
                    booked.stream().map(BookedRooms::getRoom).toList());
        }

        //  Persist new status
        reservation.setReservationStatus(newStatus);
        reservationRepo.save(reservation);
    }

    @Override
    public List<ReservationResponseDTO> getAllReservations() {
        return reservationRepo.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<ReservationResponseDTO> getReservationsByCustomer(Integer customerId) {
        if (!customerRepo.existsById(customerId)) {
            throw new RuntimeException("Customer not found: " + customerId);
        }
        return reservationRepo.findByCustomerId(customerId)
                .stream()
                .map(this::toResponse) //mapper.map(r, ReservationResponseDTO.class);
                .toList();
    }

    private ReservationResponseDTO toResponse(Reservation reservation) {
        ReservationResponseDTO dto = mapper.map(reservation, ReservationResponseDTO.class);
        dto.setBookedRoomIds(
                reservation.getBookedRooms()
                        .stream()
                        .map(br -> br.getRoom().getId())
                        .toList());
        return dto;
    }

}
