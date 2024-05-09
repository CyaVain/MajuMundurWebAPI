package com.majumundur.Models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "m_merchants")
public class Merchants {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
//    private String email;
//    private String password;
}
