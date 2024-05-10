package com.majumundur.Security.Models.DTO.Response;

import com.majumundur.Utils.RoleEnum;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MerchantCreatedResponse {
    private String merchantId;
    private String merchantName;
    private String merchantPhoneNumber;
    private String merchantEmail;
    private RoleEnum role;
}
