package com.majumundur.Models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter // Getter
@Setter // Setter
@AllArgsConstructor // Constructor Dengan Parameter / Args
@NoArgsConstructor // Constructor tanpa parameter / args
@Entity // Annotasi Jakarta untuk menandakan ini Entity
@Builder // Annotasi Lombok untuk mempermudah inisiasi object
@Table(name = "m_customers") // Annotasi untuk memberikan nama table pada database
public class Customers {
    @Id // Annotasi untuk Menandakan Attribute Yang Merupakan Primary Key
    @GeneratedValue(strategy = GenerationType.UUID) // Annotasi untuk set bahwa id di Generate Dengan UUID
    private String id;
    private String name;

    @Column(name = "phone_number")// Menuliskan nama kolom untuk di database
    private String phoneNumber;

    @Column(name = "birth_date")
    private Date birthDate;
    private String email;
    private String password;
    @Column(name = "reward_points")
    private Integer rewardPoints;
}
