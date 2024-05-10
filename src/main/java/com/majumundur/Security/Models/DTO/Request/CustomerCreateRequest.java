package com.majumundur.Security.Models.DTO.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerCreateRequest {

    @Schema(example = "Kevin Halim")
    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Name is Too Short, Min 3 characters")
    @Size(max = 25, message = "Name is Too Long, Max 25 characters")
    private String name;

    @Schema(example = "+62899012345")
    @NotBlank(message = "Phone number must not be blank")
    @Pattern(regexp = "\\+62\\d{9,13}$", message = "Phone number must begin with +62 and have 10 to 14 additional digits")
    @Size(max = 15, message = "Phone number is Too Long, Maximum 15 digits")
    private String phoneNumber;

    @Schema(example = "2000-12-31")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Birth date must not be null")
    @NotBlank(message = "Birth Date Must Not Be Empty")
    private String birthDate;

    @Schema(example = "customer123@gmail.com")
    @NotBlank(message = "Email must Not be Blank")
    @Size(min = 5, message = "Email is Too Short, Min 5 Characters")
    @Size(max = 25, message = "Email is Too Long, Max 25 characters")
    @Pattern(regexp = "^[a-z0-9]+@.*$",
            message = "Email must be valid and contain only lowercase letters and numbers.")
    private String email;

    @Schema(example = "Customer123")
    @Size(min = 5, message = "Password is Too Short, Min 5 Characters")
    @Size(max = 20, message = "Password Is Too Long, Max 20 Characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]+$",
            message = "Password Must Contain at least 1 lowercase letter, 1 uppercase letter, and 1 number")
    private String password;
}
