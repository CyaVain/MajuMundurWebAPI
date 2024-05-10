package com.majumundur.Security.Models.DTO.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequest {

    @NotBlank(message = "email must not be empty")
    @Schema(example = "superadmin@gmail.com", description = "Super Admin Email")
    private String email;

    @NotBlank(message = "password must not be empty")
    @Schema(example = "superadmin123", description = "Super Admin Password")
    private String password;
}
