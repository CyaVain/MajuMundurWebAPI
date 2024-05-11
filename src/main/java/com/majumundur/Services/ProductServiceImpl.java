package com.majumundur.Services;

import com.majumundur.Models.DTO.Requests.ProductCreateRequest;
import com.majumundur.Models.DTO.Requests.ProductUpdateRequest;
import com.majumundur.Models.Merchants;
import com.majumundur.Models.Products;
import com.majumundur.Repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Products updateProduct(ProductUpdateRequest request) {
        Products products = getProduct(request.getProductId());
        products.setId(request.getProductId());
        products.setName(request.getName());
        products.setCode(request.getCode());
        products.setPrice(request.getPrice());
        products.setDescription(request.getDescription());
        products.setMerchant(products.getMerchant());
        repository.save(products);

        return products;
    }

    @Override
    public Page<Products> getAllProducts(Pageable pageable, String merchantId) {
        return repository.findByMerchant_Id(pageable,merchantId);
    }

    @Override
    public void deleteProduct(Products products) {
        repository.delete(products);
    }

    @Override
    public Products getProduct(String id) {
        Optional<Products> product = repository.findById(id);
        if(product.isEmpty()){
            return  null;
        }
        return  product.get();
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
