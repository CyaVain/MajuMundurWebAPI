package com.majumundur.Security.Services;

import com.majumundur.Security.Models.DTO.Request.CustomerRegisterRequest;
import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Security.Models.DTO.Request.LoginRequest;
import com.majumundur.Security.Models.DTO.Request.MerchantRegisterRequest;

public interface AuthenticationService {
    ControllerResponse<?> registerAsCustomer(CustomerRegisterRequest request);
    ControllerResponse<?> registerAsMerchant(MerchantRegisterRequest request);

    ControllerResponse<?> login(LoginRequest request);
}
