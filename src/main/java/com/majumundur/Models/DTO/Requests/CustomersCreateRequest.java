package com.majumundur.Models.DTO.Requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomersCreateRequest {

    @Schema(example = "John Doe")
    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Name is Too Short, Min 3 characters")
    @Size(max = 25, message = "Name is Too Long, Max 25 characters")
    private String name;

    @Schema(example = "+62899012345")
    @NotBlank(message = "Phone number must not be blank")
    @Pattern(regexp = "\\+62\\d{9,13}$", message = "Phone number must begin with +62 and have 10 to 14 additional digits")
    @Size(max = 15, message = "Phone number is Too Long, Maximum 15 digits")
    private String phoneNumber;

    @Schema(example = "example@example.com")
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(example = "2000-01-01")
    @NotNull(message = "Birth date must not be null")
    @Past(message = "Birth date must be in the past")
    private Date birthDate;
}
