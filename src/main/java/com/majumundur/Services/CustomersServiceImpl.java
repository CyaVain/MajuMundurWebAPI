package com.majumundur.Services;

import com.majumundur.Models.Customers;
import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Models.DTO.Responses.CustomerDetailsResponse;
import com.majumundur.Security.Models.DTO.Request.CustomerCreateRequest;
import com.majumundur.Repositories.CustomersRepository;
import com.majumundur.Security.Models.UserCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;

@Service
public class CustomersServiceImpl implements CustomersService {

    private CustomersRepository repository;

    public CustomersServiceImpl(CustomersRepository repository) {
        this.repository = repository;
    }

    private final static String NOT_FOUND_MESSAGE = "Customer Not Found / Invalid Customer Id";
    @Override
    public ControllerResponse<?> getById(String id) {
        try{
            Customers customers = getCustomer(id);
            if(customers == null){
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setData(NOT_FOUND_MESSAGE);
                return response;
            }
            CustomerDetailsResponse dto = CustomerDetailsResponse.builder()
                    .customerId(customers.getId())
                    .name(customers.getName())
                    .birthDate(customers.getBirthDate().toString())
                    .phoneNumber(customers.getPhoneNumber())
                    .email(customers.getEmail())
                    .currentPoints(customers.getRewardPoints())
                    .build();

            ControllerResponse<CustomerDetailsResponse> response = ControllerResponse.<CustomerDetailsResponse>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(dto)
                    .build();

            return response;
        }
        catch (Exception e){
            ControllerResponse<String> response = new ControllerResponse<>();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response.setData(e.getMessage());
            return response;
        }
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

    @Override
    public Customers savePoint(Customers customer, Integer points) {
        //Dilakukan SetId karena pada Models GeneratedValue UUID
        //Sehingga apabila ada perubahan repository.save() maka ID akan terus berubah jika tidak di set
        customer.setId(customer.getId());
        customer.setRewardPoints(customer.getRewardPoints() + points);
        repository.save(customer);

        return customer;
    }

    @Override
    public Customers reducePoint(Customers customer, Integer points) {
        customer.setId(customer.getId());
        customer.setRewardPoints(customer.getRewardPoints() - points);
        repository.save(customer);

        return customer;
    }

    @Override
    public Customers getCustomer(String id) {
        Optional<Customers> customer = repository.findById(id);
        if(customer.isEmpty()){
            return  null;
        }
        return customer.get();
    }
}
