package com.majumundur.Services;

import com.majumundur.Models.DTO.Requests.ProductCreateRequest;
import com.majumundur.Models.DTO.Requests.ProductUpdateRequest;
import com.majumundur.Models.Products;

import java.util.Optional;

public interface ProductService {

    public Products createNewProduct(ProductCreateRequest request);
    public Products updateProduct(ProductUpdateRequest request);

    public Products getProductByCode(String code);

    public Products getProduct(String id);

    public void save(Products products);
}
