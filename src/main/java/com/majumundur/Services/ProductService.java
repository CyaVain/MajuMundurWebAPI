package com.majumundur.Services;

import com.majumundur.Models.DTO.Requests.ProductCreateRequest;
import com.majumundur.Models.DTO.Requests.ProductUpdateRequest;
import com.majumundur.Models.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public Products createNewProduct(ProductCreateRequest request);
    public Products updateProduct(ProductUpdateRequest request);

    public Products getProductByCode(String code);

    public Products getProduct(String id);
    public Page<Products> getAllProducts(Pageable pageable, String merchantId);
    public void deleteProduct(Products products);

    public void save(Products products);
}
