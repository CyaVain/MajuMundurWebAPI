package com.majumundur.Services;

import com.majumundur.Models.Customers;
import com.majumundur.Security.Models.DTO.Request.CustomerCreateRequest;
import com.majumundur.Repositories.CustomerRepository;
import com.majumundur.Security.Models.UserCredentials;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public Customers save(CustomerCreateRequest request, UserCredentials credentials) {
        Customers customer = Customers.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .birthDate(Date.valueOf(request.getBirthDate()))
                .email(credentials.getEmail())
                .password(credentials.getPassword())
                .rewardPoints(0)
                .build();
        repository.save(customer);

        return customer;
    }
}
