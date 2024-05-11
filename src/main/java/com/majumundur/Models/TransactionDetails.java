package com.majumundur.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "trx_transaction_details")
public class TransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private Transactions transaction;

    @ManyToOne
    private Products products;

    @Column(name = "sales_price")
    private Double salesPrice;
}
