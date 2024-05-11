package com.majumundur.Models.DTO.Responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductCreatedResponse {

    private String merchantId;
    private String merchantName;
    private String productId;
    private String productCode;
    private String productName;
    private String productDescription;
    private Double productPrice;
}
