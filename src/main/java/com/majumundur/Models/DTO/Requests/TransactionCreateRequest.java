package com.majumundur.Models.DTO.Requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionCreateRequest {

    @NotBlank(message = "Merchant Id  must not be blank")
    private String merchantId;

    @NotBlank(message = "Customer Id must not be blank")
    private String customerId;

    @NotBlank(message = "Product Id must not be blank")
    private String productId;
}
