package edu.esoft.com.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BookedRooms")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookedRooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
}
