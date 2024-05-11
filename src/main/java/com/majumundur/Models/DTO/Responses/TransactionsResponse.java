package com.majumundur.Models.DTO.Responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionsResponse {

    private String transactionDate;
    private String merchantId;
    private String merchantName;
    private String customerId;
    private String customerName;
    private String productId;
    private String productCode;
    private String productName;
    private Double price;
    private Integer earnedPoints;
}
