package com.majumundur.Models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "trx_reward_transactions")
public class RewardTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @ManyToOne
    private Customers customer;

    @ManyToOne
    private Rewards reward;
}
