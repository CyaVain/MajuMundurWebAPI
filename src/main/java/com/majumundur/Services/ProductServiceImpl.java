package com.majumundur.Services;

import com.majumundur.Models.DTO.Requests.ProductCreateRequest;
import com.majumundur.Models.Merchants;
import com.majumundur.Models.Products;
import com.majumundur.Repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Products createNewProduct(ProductCreateRequest request) {
        Products product = Products.builder()
                .name(request.getName())
                .code(request.getCode())
                .price(request.getPrice())
                .description(request.getDescription())
                .merchant(null)
                .build();
        repository.save(product);
        return product;
    }

    @Override
    public Products getProductByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public void save(Products products) {
        repository.save(products);
    }
}
