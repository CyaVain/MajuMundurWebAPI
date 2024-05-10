package com.majumundur.Models.DTO.Requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RewardsCreateRequest {

    @Schema(example = "R001")
    @NotBlank(message = "Code Must Not Be Blank")
    @Size(min = 3, message = "Code is Too Short, Min 3 characters")
    @Size(max = 10, message = "Code is Too Long, Max 10 characters")
    private String rewardsCode;

    @Schema(example = "Honda Brio")
    @NotBlank(message = "Name Must Not Be Blank")
    @Size(min = 3, message = "Name is Too Short, Min 3 characters")
    @Size(max = 25, message = "Name is Too Long, Max 25 characters")
    private String rewardsName;

    @Schema(example = "10")
    @NotNull(message = "RewardsPoints Must Not Be Null")
    @Min(value = 1, message = "Point is Too Low, Min 1")
    @Max(value = 1000, message = "Point is Too High, Max 1000")
    private Integer requiredPoints;
}
