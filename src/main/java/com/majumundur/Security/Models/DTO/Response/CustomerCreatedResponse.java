package com.majumundur.Security.Models.DTO.Response;

import com.majumundur.Utils.RoleEnum;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerCreatedResponse {
    private String customerId;
    private String customerName;
    private String customerPhoneNumber;
    private String customerBirthDate;
    private String customerEmail;
    private RoleEnum role;
}
