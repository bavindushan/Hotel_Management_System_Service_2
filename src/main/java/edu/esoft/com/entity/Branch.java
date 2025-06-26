package edu.esoft.com.entity;

import jakarta.persistence.Entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Branch")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(length = 100)
    private String email;

    @Column(name = "tel_no", length = 20)
    private String telNo;
}
