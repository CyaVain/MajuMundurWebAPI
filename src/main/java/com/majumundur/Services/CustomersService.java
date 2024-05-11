package com.majumundur.Services;

import com.majumundur.Models.Customers;
import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Security.Models.DTO.Request.CustomerCreateRequest;
import com.majumundur.Security.Models.UserCredentials;

public interface CustomersService {

    public ControllerResponse<?> getById(String id);

    public Customers save(CustomerCreateRequest request, UserCredentials credentials);

    public Customers reducePoint(Customers customer, Integer points);
    public Customers savePoint(Customers customer, Integer points);
    public Customers getCustomer(String id);
}
