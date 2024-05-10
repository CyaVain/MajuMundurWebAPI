package com.majumundur.Controllers;

import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Security.DTO.Request.CustomerCreateRequest;
import com.majumundur.Security.DTO.Request.LoginRequest;
import com.majumundur.Security.Services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentication Controller", description = "Controller To Register As Customer, Register As Merchant, and Login")
@RequestMapping("/auth")
public class AuthenticationController {
    private AuthenticationService service;

    public AuthenticationController(AuthenticationService service){
        this.service = service;
    }

    @Operation(summary = "Login" , description = "Login to Get Token")
    @PostMapping(path = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ControllerResponse> Login(@RequestBody LoginRequest request){
        ControllerResponse<?> response = service.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Register As Customer" , description = "Register As A Customer and Gain Role CUSTOMER")
    @PostMapping(path = "/register-customer",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ControllerResponse> RegisterAsCustomer(@RequestBody CustomerCreateRequest request){
        ControllerResponse<?> response = service.registerAsCustomer(request);
        return ResponseEntity.ok(response);
    }
}
