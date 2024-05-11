package com.majumundur.Controllers;

import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Customer Controller", description = "Controller To Get Customer Details")
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @Operation(summary = "Get Customer's Details" , description = "Get A Customer's Details")
    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomerDetails(@PathVariable String customerId){
        ControllerResponse<?> response = service.getById(customerId);
        return ResponseEntity.ok(response);
    }
}
