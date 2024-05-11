package com.majumundur.Models.DTO.Responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MerchantResponse {

    private String merchantId;
    private String merchantName;
    private String merchantPhoneNumber;
    private String merchantEmail;
    private Integer totalProducts;

}
