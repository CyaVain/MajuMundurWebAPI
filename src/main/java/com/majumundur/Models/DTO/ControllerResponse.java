package com.majumundur.Models.DTO;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder // Annotasi untuk build objek lebih mudah
public class ControllerResponse<T> {
    private Integer statusCode;
    private String message;
    private T data;
}
