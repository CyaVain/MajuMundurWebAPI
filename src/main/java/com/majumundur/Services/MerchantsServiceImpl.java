package com.majumundur.Services;

import com.majumundur.Models.DTO.Requests.ProductCreateRequest;
import com.majumundur.Models.DTO.Requests.ProductUpdateRequest;
import com.majumundur.Models.DTO.Responses.*;
import com.majumundur.Models.Merchants;
import com.majumundur.Models.Products;
import com.majumundur.Repositories.MerchantsRepository;
import com.majumundur.Security.Models.DTO.Request.MerchantRegisterRequest;
import com.majumundur.Security.Models.UserCredentials;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MerchantsServiceImpl implements MerchantsService {
    private MerchantsRepository repository;
    private ProductsService productsService;
    private ValidationService validation;
    private final static String NOT_FOUND_MESSAGE = "Merchant Not Found / Invalid Merchant Id";

    public MerchantsServiceImpl(MerchantsRepository repository, ProductsService productsService, ValidationService validation) {
        this.repository = repository;
        this.productsService = productsService;
        this.validation = validation;
    }

    @Transactional
    @Override
    public Merchants save(MerchantRegisterRequest request, UserCredentials credentials) {
        Merchants merchant = Merchants.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .email(credentials.getEmail())
                .password(credentials.getPassword())
                .productsList(null)
                .build();
        repository.save(merchant);
        return merchant;
    }

    @Override
    public ControllerResponse<?> getById(String id) {
        try{
            Optional<Merchants> merchants = repository.findById(id);
            if(merchants.isEmpty()){
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setData(NOT_FOUND_MESSAGE);
                return response;
            }
            MerchantResponse dto = MerchantResponse.builder()
                    .merchantId(merchants.get().getId())
                    .merchantName(merchants.get().getName())
                    .merchantPhoneNumber(merchants.get().getPhoneNumber())
                    .merchantEmail(merchants.get().getEmail())
                    .totalProducts(merchants.get().getProductsList().size())
                    .build();

            ControllerResponse<MerchantResponse> response = ControllerResponse.<MerchantResponse>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(dto)
                    .build();

            return response;
        }
        catch (Exception e){
            ControllerResponse<String> response = new ControllerResponse<>();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response.setData(e.getMessage());
            return  response;
        }
    }

    @Transactional
    @Override
    public ControllerResponse<?> createProduct(ProductCreateRequest request, String merchantId) {
        try{
            List<String> violations = validation.validate(request);
            if(violations != null) {
                ControllerResponse<List<String>> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
                response.setData(violations);
                return response;
            }
            Merchants merchant = getMerchant(merchantId);

            if(merchant == null){
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setData(NOT_FOUND_MESSAGE);
                return response;
            }

            if(productsService.getProductByCode(request.getCode()) != null){
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.CONFLICT.value());
                response.setMessage(HttpStatus.CONFLICT.getReasonPhrase());
                response.setData("Product's Code Already Exists");
                return response;
            }

            Products product = productsService.createNewProduct(request);
            product.setMerchant(merchant);
            productsService.save(product);

            List<Products> productsList = merchant.getProductsList();
            productsList.add(product);
            merchant.setProductsList(productsList);
            repository.save(merchant);

            ProductResponse dto = ProductResponse.builder()
                    .merchantId(merchant.getId())
                    .merchantName(merchant.getName())
                    .productId(product.getId())
                    .productName(product.getName())
                    .productCode(product.getCode())
                    .productDescription(product.getDescription())
                    .productPrice(product.getPrice())
                    .build();

            ControllerResponse<ProductResponse> response = ControllerResponse.<ProductResponse>builder()
                    .statusCode(HttpStatus.CREATED.value())
                    .message(HttpStatus.CREATED.getReasonPhrase())
                    .data(dto)
                    .build();

            return response;
        }
        catch (Exception e){
            ControllerResponse<String> response = new ControllerResponse<>();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response.setData(e.getMessage());
            return  response;
        }
    }

    @Transactional
    @Override
    public ControllerResponse<?> updateProduct(ProductUpdateRequest request, String merchantId) {
        try{
            List<String> violations = validation.validate(request);
            if(violations != null) {
                ControllerResponse<List<String>> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
                response.setData(violations);
                return response;
            }
            Merchants merchant = getMerchant(merchantId);

            if(merchant == null){
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setData(NOT_FOUND_MESSAGE);
                return response;
            }

            if(productsService.getProduct(request.getProductId()) == null){
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setData("Product Not Found / Invalid Product Id");
                return response;
            }

            Products product = productsService.updateProduct(request);
            productsService.save(product);

            List<Products> productsList = merchant.getProductsList();
            merchant.setProductsList(productsList);
            repository.save(merchant);

            ProductResponse dto = ProductResponse.builder()
                    .merchantId(merchant.getId())
                    .merchantName(merchant.getName())
                    .productId(product.getId())
                    .productName(product.getName())
                    .productCode(product.getCode())
                    .productDescription(product.getDescription())
                    .productPrice(product.getPrice())
                    .build();

            ControllerResponse<ProductResponse> response = ControllerResponse.<ProductResponse>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(dto)
                    .build();

            return response;
        }
        catch (Exception e){
            ControllerResponse<String> response = new ControllerResponse<>();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response.setData(e.getMessage());
            return  response;
        }
    }

    @Override
    public ControllerResponse<?> viewProducts(Pageable pageable, String merchantId) {
            Merchants merchants = getMerchant(merchantId);
            if(merchants == null){
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setData(NOT_FOUND_MESSAGE);
                return response;
            }
            Page<Products> products = productsService.getAllProducts(pageable,merchantId);
            if(products.isEmpty()){
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setData("ProductList Is Empty");
                return response;
            }
            List<ProductDetailsResponse> dto = new ArrayList<>();
            for(Products p : products){
                ProductDetailsResponse viewResponse = ProductDetailsResponse.builder()
                        .merchantId(p.getMerchant().getId())
                        .productId(p.getId())
                        .name(p.getName())
                        .code(p.getCode())
                        .description(p.getDescription())
                        .price(p.getPrice())
                        .build();
                dto.add(viewResponse);
            }

            PagingResponse<List<ProductDetailsResponse>> pagingResponse = PagingResponse.<List<ProductDetailsResponse>>builder()
                    .totalElements((int)products.getTotalElements())
                    .totalPages(products.getTotalPages())
                    .currentPage(products.getNumber())
                    .size(products.getSize())
                    .data(dto)
                    .build();

            ControllerResponse<PagingResponse> response = new ControllerResponse<>();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage(HttpStatus.OK.getReasonPhrase());
            response.setData(pagingResponse);

            return response;
    }

    @Transactional
    @Override
    public ControllerResponse<?> deleteProduct(String productId, String merchantId) {
        try{
            Merchants merchant = getMerchant(merchantId);
            if(merchant == null)
            {
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setData(NOT_FOUND_MESSAGE);
                return response;
            }
            Products product = productsService.getProduct(productId);
            if(product == null){
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setData("Product Not Found / Invalid Product Id");
                return response;
            }
            List<Products> productsList = merchant.getProductsList();
            productsList.remove(product);

            productsService.deleteProduct(product);
            ControllerResponse<String> response = ControllerResponse.<String>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data("Product Deleted")
                    .build();

            return response;
        }
        catch (Exception e){
            ControllerResponse<String> response = new ControllerResponse<>();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response.setData(e.getMessage());
            return  response;
        }
    }

    @Override
    public ControllerResponse<?> getProductDetails(String productId, String merchantId) {
        try{
            Merchants merchant = getMerchant(merchantId);
            if(merchant == null)
            {
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setData(NOT_FOUND_MESSAGE);
                return response;
            }
            Products product = productsService.getProduct(productId);
            if(product == null){
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setData("Product Not Found / Invalid Product Id");
                return response;
            }

            ProductDetailsResponse dto = ProductDetailsResponse.builder()
                    .merchantId(product.getMerchant().getId())
                    .productId(product.getId())
                    .name(product.getName())
                    .code(product.getCode())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .build();

            ControllerResponse<ProductDetailsResponse> response = new ControllerResponse<>();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage(HttpStatus.OK.getReasonPhrase());
            response.setData(dto);
            return response;
        }
        catch (Exception e){
            ControllerResponse<String> response = new ControllerResponse<>();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response.setData(e.getMessage());
            return  response;
        }
    }

    @Override
    public Merchants getMerchant(String id) {
        Optional<Merchants> merchants = repository.findById(id);
        return merchants.orElse(null);
    }
}
