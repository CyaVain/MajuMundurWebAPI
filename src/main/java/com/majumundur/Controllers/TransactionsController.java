package com.majumundur.Controllers;

import com.majumundur.Models.DTO.Requests.TransactionCreateRequest;
import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Services.TransactionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Transactions Controller", description = "Controller To Create Transaction, View All Transactions")
@RequestMapping("/api/transactions")
public class TransactionsController {

    private TransactionsService service;

    public TransactionsController(TransactionsService service) {
        this.service = service;
    }

    @Operation(summary = "Create A Transaction CUSTOMER OR SUPER_ADMIN ONLY" , description = "Create New Transaction, CUSTOMER OR SUPER_ADMIN ONLY !!")
    @PreAuthorize("hasAnyRole('CUSTOMER','SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionCreateRequest request){
        ControllerResponse<?> response = service.createTransaction(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "View Transaction History CUSTOMER, MERCHANT, OR SUPER_ADMIN ONLY" , description = "View Transaction's Detail History, CUSTOMER, MERCHANT, OR SUPER_ADMIN ONLY")
    @PreAuthorize("hasAnyRole('CUSTOMER','SUPER_ADMIN','MERCHANT')")
    @GetMapping("/{transactionId}")
    public ResponseEntity<?> viewTransactionHistory(@PathVariable String transactionId){
        ControllerResponse<?> response = service.getTransactionHistory(transactionId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "View Customer's History CUSTOMER, MERCHANT, OR SUPER_ADMIN ONLY" , description = "View Customer's Transaction History, CUSTOMER, MERCHANT, OR SUPER_ADMIN ONLY")
    @PreAuthorize("hasAnyRole('CUSTOMER','SUPER_ADMIN','MERCHANT')")
    @GetMapping()
    public ResponseEntity<?> getCustomerHistories(){
        ControllerResponse<?> response = service.getCustomerHistories();
        return ResponseEntity.ok(response);
    }
}
