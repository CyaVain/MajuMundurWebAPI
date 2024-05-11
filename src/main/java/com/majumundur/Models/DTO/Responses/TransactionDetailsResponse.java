package com.majumundur.Models.DTO.Responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionDetailsResponse {
    private String transactionDate;
    private String customerId;
    private String customerName;
    private String productId;
    private String productName;
    private String productCode;
    private Double salesPrice;
}
