package com.majumundur.Models;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "m_products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String code;
    private String name;
    private String description;
    private Double price;

    @ManyToOne
    private Merchants merchant;
}
