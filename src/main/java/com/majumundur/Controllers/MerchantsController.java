package com.majumundur.Controllers;

import com.majumundur.Models.DTO.Requests.ProductCreateRequest;
import com.majumundur.Models.DTO.Requests.ProductUpdateRequest;
import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Models.Merchants;
import com.majumundur.Services.MerchantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Tag(name = "Merchants Controller", description = "Controller To Get Merchant Details by Id, Create New Product")
@RequestMapping("/api/merchants")
public class MerchantsController {
    private MerchantService service;
    public MerchantsController(MerchantService service){
        this.service = service;
    }

    @Operation(summary = "View Merchant Details by Id" , description = "View Merchant Details By It's Id")
    @GetMapping("/{merchantId}")
    public ResponseEntity<?> getById(@PathVariable String merchantId){
        ControllerResponse<?> response = service.getById(merchantId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create New Product " , description = "Create A Product, MERCHANTS & SUPER_ADMIN ONLY !!")
    @PreAuthorize("hasAnyRole('MERCHANT','SUPER_ADMIN')")
    @PostMapping("/add-product")
    public ResponseEntity<?> addProduct(@RequestBody ProductCreateRequest request){
        ControllerResponse<?> response = service.createProduct(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update Product " , description = "Update A Product, MERCHANTS & SUPER_ADMIN ONLY !!")
    @PreAuthorize("hasAnyRole('MERCHANT','SUPER_ADMIN')")
    @PutMapping("/{merchantId}/update-product")
    public ResponseEntity<?> updateProduct(@RequestBody ProductUpdateRequest request,
                                           @PathVariable String merchantId){
        ControllerResponse<?> response = service.updateProduct(request,merchantId);
        return ResponseEntity.ok(response);
    }
}
