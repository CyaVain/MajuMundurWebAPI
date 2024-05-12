package com.majumundur.Controllers;

import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Security.Models.DTO.Request.CustomerRegisterRequest;
import com.majumundur.Security.Models.DTO.Request.LoginRequest;
import com.majumundur.Security.Models.DTO.Request.MerchantRegisterRequest;
import com.majumundur.Security.Services.AuthenticationService;
import com.majumundur.Services.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Authentication Controller", description = "Controller To Register As Customer, Register As Merchant, and Login")
@RequestMapping("/auth")
public class AuthenticationController {
    private AuthenticationService service;

    @Autowired
    public ProductsService productsService;

    public AuthenticationController(AuthenticationService service){
        this.service = service;
    }

    @Operation(summary = "Login" , description = "Login to Get Token")
    @PostMapping(path = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ControllerResponse> login(@RequestBody LoginRequest request){
        ControllerResponse<?> response = service.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Register As Customer" , description = "Register As A Customer and Gain Role AS CUSTOMER")
    @PostMapping(path = "/register-as-customer",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ControllerResponse> registerAsCustomer(@RequestBody CustomerRegisterRequest request){
        ControllerResponse<?> response = service.registerAsCustomer(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Register As Merchant" , description = "Register As A Merchant and Gain Role AS MERCHANT")
    @PostMapping(path = "/register-as-merchant",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ControllerResponse> registerAsMerchant(@RequestBody MerchantRegisterRequest request){
        ControllerResponse<?> response = service.registerAsMerchant(request);
        return ResponseEntity.ok(response);
    }
}
