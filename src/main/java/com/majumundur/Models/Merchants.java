package com.majumundur.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "m_merchants")
public class Merchants {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String email;
    private String password;

    @OneToMany
    private List<Products> productsList;
}