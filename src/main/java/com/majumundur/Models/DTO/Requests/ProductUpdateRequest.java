package com.majumundur.Models.DTO.Requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductUpdateRequest {

    @NotBlank(message = "MerchantId Must Not Be Empty")
    private String productId;

    @Schema(example = "PR001")
    @NotBlank(message = "Code must not be blank")
    @Size(min = 3, message = "Code is Too Short, Min 3 characters")
    @Size(max = 10, message = "Code is Too Long, Max 10 characters")
    private String code;

    @Schema(example = "Laptop HP Envy")
    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Name is Too Short, Min 3 characters")
    @Size(max = 50, message = "Name is Too Long, Max 50 characters")
    private String name;

    @Schema(example = "Most Recommended Laptop For Gaming & Work")
    @NotBlank(message = "Description must not be blank")
    @Size(min = 10, max = 200, message = "Description Must Between 10 and 200 Characters")
    private String description;

    @Schema(example = "65000")
    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0.0")
    private Double price;
}
