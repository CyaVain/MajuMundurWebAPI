package com.majumundur.Services;

import com.majumundur.Models.Customers;
import com.majumundur.Security.DTO.Request.CustomerCreateRequest;
import com.majumundur.Security.Models.UserCredentials;

public interface CustomerService {

    public Customers Create(CustomerCreateRequest request, UserCredentials credentials);
}
