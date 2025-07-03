package edu.esoft.com.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reservation_payment_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationPaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "id", unique = true)
    private Reservation reservation;

    @Column(name = "card_type", length = 20)
    private String cardType;

    @Column(name = "card_number", length = 100)
    private String cardNumber;

    @Column(name = "card_exp_month", length = 2)
    private String cardExpMonth;

    @Column(name = "card_exp_year", length = 4)
    private String cardExpYear;

    @Column(name = "cvn_code", length = 10)
    private String cvnCode;
}
