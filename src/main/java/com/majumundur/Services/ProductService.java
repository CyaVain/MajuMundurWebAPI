package com.majumundur.Services;

import com.majumundur.Models.DTO.Requests.ProductCreateRequest;
import com.majumundur.Models.Products;

public interface ProductService {

    public Products createNewProduct(ProductCreateRequest request);
}
