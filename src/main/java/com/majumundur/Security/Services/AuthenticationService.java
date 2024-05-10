package com.majumundur.Security.Services;

import com.majumundur.Security.DTO.Request.CustomerCreateRequest;
import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Security.DTO.Request.LoginRequest;

public interface AuthenticationService {
    ControllerResponse<?> registerAsCustomer(CustomerCreateRequest request);
    ControllerResponse<?> login(LoginRequest request);
}
