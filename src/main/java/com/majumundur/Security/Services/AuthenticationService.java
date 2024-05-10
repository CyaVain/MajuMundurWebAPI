package com.majumundur.Security.Services;

import com.majumundur.Security.Models.DTO.Request.CustomerCreateRequest;
import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Security.Models.DTO.Request.LoginRequest;
import com.majumundur.Security.Models.DTO.Request.MerchantCreateRequest;

public interface AuthenticationService {
    ControllerResponse<?> registerAsCustomer(CustomerCreateRequest request);
    ControllerResponse<?> registerAsMerchant(MerchantCreateRequest request);

    ControllerResponse<?> login(LoginRequest request);
}
