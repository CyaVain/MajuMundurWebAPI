package com.majumundur.Models.DTO.Responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductViewResponse {

    private String productId;
    private String name;
    private String code;
    private String description;
    private Double price;
}
