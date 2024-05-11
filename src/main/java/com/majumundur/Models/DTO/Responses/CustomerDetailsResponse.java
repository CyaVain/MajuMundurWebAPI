package com.majumundur.Models.DTO.Responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDetailsResponse {

    private String customerId;
    private String name;
    private String phoneNumber;
    private String birthDate;
    private String email;
    private Integer currentPoints;
}
