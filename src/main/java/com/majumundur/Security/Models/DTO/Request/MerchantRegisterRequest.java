package com.majumundur.Security.Models.DTO.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MerchantRegisterRequest {

    @Schema(example = "Toko Merdeka Official Store")
    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Name is Too Short, Min 3 characters")
    @Size(max = 35, message = "Name is Too Long, Max 35 characters")
    private String name;

    @Schema(example = "+628123456789")
    @NotBlank(message = "Phone number must not be blank")
    @Pattern(regexp = "\\+62\\d{9,13}$", message = "Phone number must begin with +62 and have 10 to 14 additional digits")
    @Size(max = 15, message = "Phone number is Too Long, Maximum 15 digits")
    private String phoneNumber;

    @Schema(example = "merchant123@gmail.com")
    @NotBlank(message = "Email must Not be Blank")
    @Size(min = 5, message = "Email is Too Short, Min 5 Characters")
    @Size(max = 25, message = "Email is Too Long, Max 25 characters")
    @Pattern(regexp = "^[a-z0-9]+@.*$",
            message = "Email must be valid and contain only lowercase letters and numbers.")
    private String email;

    @Schema(example = "Merchant123")
    @Size(min = 5, message = "Password is Too Short, Min 5 Characters")
    @Size(max = 20, message = "Password Is Too Long, Max 20 Characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]+$",
            message = "Password Must Contain at least 1 lowercase letter, 1 uppercase letter, and 1 number")
    private String password;

}
