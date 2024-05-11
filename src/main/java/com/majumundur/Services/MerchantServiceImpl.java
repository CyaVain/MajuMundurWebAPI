package com.majumundur.Services;

import com.majumundur.Models.DTO.Requests.ProductCreateRequest;
import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Models.DTO.Responses.MerchantResponse;
import com.majumundur.Models.DTO.Responses.ProductCreatedResponse;
import com.majumundur.Models.Merchants;
import com.majumundur.Models.Products;
import com.majumundur.Repositories.MerchantRepository;
import com.majumundur.Security.Models.DTO.Request.MerchantCreateRequest;
import com.majumundur.Security.Models.UserCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MerchantServiceImpl implements MerchantService {
    private MerchantRepository repository;
    private ProductService productService;
    private ValidationService validation;

    public MerchantServiceImpl(MerchantRepository repository, ProductService productService, ValidationService validation) {
        this.repository = repository;
        this.productService = productService;
        this.validation = validation;
    }

    @Transactional
    @Override
    public Merchants save(MerchantCreateRequest request, UserCredentials credentials) {
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
                response.setData("Merchant Not Found / Invalid Merchant Id");
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
    public ControllerResponse<?> createProduct(ProductCreateRequest request) {
        try{
            List<String> violations = validation.validate(request);
            if(violations != null) {
                ControllerResponse<List<String>> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
                response.setData(violations);
                return response;
            }
            Merchants merchant = getMerchant(request.getMerchantId());

            if(merchant == null){
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setData("Merchant Not Found / Invalid Merchant Id");
                return response;
            }

            if(productService.getProductByCode(request.getCode()) != null){
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.CONFLICT.value());
                response.setMessage(HttpStatus.CONFLICT.getReasonPhrase());
                response.setData("Product's Code Already Exists");
                return response;
            }

            Products product = productService.createNewProduct(request);
            product.setMerchant(merchant);
            productService.save(product);

            List<Products> productsList = merchant.getProductsList();
            productsList.add(product);
            merchant.setProductsList(productsList);
            repository.save(merchant);

            ProductCreatedResponse dto = ProductCreatedResponse.builder()
                    .merchantId(merchant.getId())
                    .merchantName(merchant.getName())
                    .productId(product.getId())
                    .productName(product.getName())
                    .productCode(product.getCode())
                    .productDescription(product.getDescription())
                    .productPrice(product.getPrice())
                    .build();

            ControllerResponse<ProductCreatedResponse> response = ControllerResponse.<ProductCreatedResponse>builder()
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

    @Override
    public Merchants getMerchant(String id) {
        Optional<Merchants> merchants = repository.findById(id);
        return merchants.orElse(null);
    }
}
