package com.majumundur.Services;

import com.majumundur.Models.DTO.Requests.ProductCreateRequest;
import com.majumundur.Models.DTO.Requests.ProductUpdateRequest;
import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Models.Merchants;
import com.majumundur.Security.Models.DTO.Request.MerchantCreateRequest;
import com.majumundur.Security.Models.UserCredentials;

public interface MerchantService {
    public ControllerResponse<?> getById(String id);
    public ControllerResponse<?> createProduct(ProductCreateRequest request);
    public ControllerResponse<?> updateProduct(ProductUpdateRequest request, String merchantId);
    public Merchants save(MerchantCreateRequest request, UserCredentials credentials);

    public Merchants getMerchant(String id);
}
