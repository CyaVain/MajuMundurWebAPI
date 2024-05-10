package com.majumundur.Services;

import com.majumundur.Models.DTO.Requests.CustomersCreateRequest;
import com.majumundur.Models.DTO.Responses.ControllerResponse;

public interface CustomerService {

    public ControllerResponse<?> Create(CustomersCreateRequest request);
    public ControllerResponse<?> GetById(String id);
    public ControllerResponse<?> GetAll();
}
