package com.majumundur.Security.Services;

import com.majumundur.Models.DTO.Requests.CustomersCreateRequest;
import com.majumundur.Security.DTO.LoginRequest;
import com.majumundur.Security.Models.UserCredentials;
import org.apache.catalina.User;

public interface AuthencticationService {
    UserCredentials registerAsCustomer(CustomersCreateRequest request);
    String login(LoginRequest request);
}
