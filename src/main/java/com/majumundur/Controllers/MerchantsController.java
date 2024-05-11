package com.majumundur.Controllers;

import com.majumundur.Models.DTO.Requests.ProductCreateRequest;
import com.majumundur.Models.DTO.Requests.ProductUpdateRequest;
import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Services.MerchantsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Merchants Controller", description = "Controller To Get Merchant Details by Id, Create, Update , & Delete New Product, View Merchant's Product List")
@RequestMapping("/api/merchants")
public class MerchantsController {
    private MerchantsService service;
    public MerchantsController(MerchantsService service){
        this.service = service;
    }

    @Operation(summary = "View Merchant Details by Id" , description = "View Merchant Details By It's Id")
    @GetMapping("/{merchantId}")
    public ResponseEntity<?> getById(@PathVariable String merchantId){
        ControllerResponse<?> response = service.getById(merchantId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create New Product MERCHANTS OR SUPER_ADMIN ONLY" , description = "Create A Product, MERCHANTS OR SUPER_ADMIN ONLY !!")
    @PreAuthorize("hasAnyRole('MERCHANT','SUPER_ADMIN')")
    @PostMapping("/{merchantId}/add-product")
    public ResponseEntity<?> addProduct(@RequestBody ProductCreateRequest request,
                                        @PathVariable String merchantId)
    {
        ControllerResponse<?> response = service.createProduct(request,merchantId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update Product MERCHANTS OR SUPER_ADMIN ONLY" , description = "Update A Product, MERCHANTS OR SUPER_ADMIN ONLY !!")
    @PreAuthorize("hasAnyRole('MERCHANT','SUPER_ADMIN')")
    @PutMapping("/{merchantId}/update-product")
    public ResponseEntity<?> updateProduct(@RequestBody ProductUpdateRequest request,
                                           @PathVariable String merchantId){
        ControllerResponse<?> response = service.updateProduct(request,merchantId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete A Product MERCHANTS OR SUPER_ADMIN ONLY" , description = "Delete A Product By It's Product Id, MERCHANT OR SUPER_ADMIN ONLY !!")
    @PreAuthorize("hasAnyRole('MERCHANT','SUPER_ADMIN')")
    @DeleteMapping("/{merchantId}/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId,
                                        @PathVariable String merchantId){
        ControllerResponse<?> response = service.deleteProduct(productId,merchantId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get Product Details" , description = "Get A Product Details")
    @GetMapping("/{merchantId}/products/{productId}")
    public ResponseEntity<?> viewProductDetails(@PathVariable String productId,
                                           @PathVariable String merchantId){
        ControllerResponse<?> response = service.getProductDetails(productId,merchantId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "View Product List " , description = "View Product List by It's Merchant Id")
    @GetMapping("/{merchantId}/products")
public ResponseEntity<?> viewProducts(@RequestParam(name = "page",required = false,defaultValue = "0") Integer page,
                                      @RequestParam(name = "size",required = false,defaultValue = "5") Integer size,
                                      @PathVariable String merchantId){
        Pageable pageable = PageRequest.of(page,size);
        ControllerResponse<?> response = service.viewProducts(pageable,merchantId);
        return ResponseEntity.ok(response);
    }
}
