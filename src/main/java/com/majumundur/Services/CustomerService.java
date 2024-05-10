package com.majumundur.Services;

import com.majumundur.Models.Customers;
import com.majumundur.Security.Models.DTO.Request.CustomerCreateRequest;
import com.majumundur.Security.Models.UserCredentials;

public interface CustomerService {

    public Customers Save(CustomerCreateRequest request, UserCredentials credentials);
}
