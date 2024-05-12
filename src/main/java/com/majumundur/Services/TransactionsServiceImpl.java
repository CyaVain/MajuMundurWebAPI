package com.majumundur.Services;

import com.majumundur.Models.*;
import com.majumundur.Models.DTO.Requests.TransactionCreateRequest;
import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Models.DTO.Responses.PagingResponse;
import com.majumundur.Models.DTO.Responses.TransactionDetailsResponse;
import com.majumundur.Models.DTO.Responses.TransactionsResponse;
import com.majumundur.Repositories.TransactionsRepository;
import org.hibernate.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    private TransactionsRepository repository;
    private CustomersService customersService;
    private MerchantsService merchantsService;
    private ProductsService productsService;
    private TransactionDetailsService detailsService;
    private ValidationService validation;

    public TransactionsServiceImpl(TransactionsRepository repository, CustomersService customersService, MerchantsService merchantsService, ProductsService productsService, TransactionDetailsService detailsService, ValidationService validation) {
        this.repository = repository;
        this.customersService = customersService;
        this.merchantsService = merchantsService;
        this.productsService = productsService;
        this.detailsService = detailsService;
        this.validation = validation;
    }

    @Transactional
    @Override
    public ControllerResponse<?> createTransaction(TransactionCreateRequest request) {
        try{
            List<String> violations = validation.validate(request);
            if(violations != null) {
                ControllerResponse<List<String>> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
                response.setData(violations);
                return response;
            }

            Customers customer = customersService.getCustomer(request.getCustomerId());
            if(customer == null){
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setData("Customer Not Found / Invalid Customer Id");
                return response;
            }

            Merchants merchant = merchantsService.getMerchant(request.getMerchantId());
            if(merchant == null)
            {
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setData("Merchant Not Found / Invalid Merchant Id");
                return response;
            }

            Products product = productsService.getProduct(request.getProductId());
            if(product == null){
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setData("Product Not Found / Invalid Product Id");
                return response;
            }

            Transactions transaction = Transactions.builder()
                    .purchaseDate(Date.from(Instant.now()))
                    .customer(customer)
                    .merchants(merchant)
                    .build();
            repository.save(transaction);

            TransactionDetails details = TransactionDetails.builder()
                    .transaction(transaction)
                    .products(product)
                    .salesPrice(product.getPrice())
                    .build();
            detailsService.saveDetails(transaction,details);

            //Menghitung Reward Point Yang Didapat Untuk Setiap keliapatan 1.000
            Double salesPrice = product.getPrice();
            Integer earnedPoints = salesPrice.intValue() / 1000;

            customersService.savePoint(customer,earnedPoints);

            TransactionsResponse dto = TransactionsResponse.builder()
                    .transactionDate(transaction.getPurchaseDate().toString())
                    .merchantId(transaction.getMerchants().getId())
                    .merchantName(transaction.getMerchants().getName())
                    .customerId(transaction.getCustomer().getId())
                    .customerName(transaction.getCustomer().getName())
                    .productId(details.getProducts().getId())
                    .productName(details.getProducts().getName())
                    .productCode(details.getProducts().getCode())
                    .price(details.getSalesPrice())
                    .earnedPoints(earnedPoints)
                    .build();

            ControllerResponse<TransactionsResponse> response = ControllerResponse.<TransactionsResponse>builder()
                    .statusCode(HttpStatus.CREATED.value())
                    .message(HttpStatus.CREATED.getReasonPhrase())
                    .data(dto)
                    .build();

            return response;
        }
        catch (Exception e){
            ControllerResponse<String> response = new ControllerResponse<>();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response.setData(e.getMessage());
            return  response;
        }
    }

    @Override
    public ControllerResponse<?> getTransactionHistory(String transactionId) {
        try{
            TransactionDetails details = detailsService.getDetails(transactionId);
            TransactionDetailsResponse dto = TransactionDetailsResponse.builder()
                    .transactionDate(details.getTransaction().getPurchaseDate().toString())
                    .customerId(details.getTransaction().getCustomer().getId())
                    .customerName(details.getTransaction().getCustomer().getName())
                    .productId(details.getProducts().getId())
                    .productName(details.getProducts().getName())
                    .productCode(details.getProducts().getCode())
                    .salesPrice(details.getProducts().getPrice())
                    .build();

            ControllerResponse<TransactionDetailsResponse> response = ControllerResponse.<TransactionDetailsResponse>builder()
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
            return  response;
        }
    }

    @Override
    public ControllerResponse<?> getCustomerHistories() {

        List<Transactions> transactions = repository.findAll();
        List<TransactionDetailsResponse> dto = new ArrayList<>();
        //Looping Untuk Melakukan Mapping Untuk DTO
        for(Transactions tr : transactions){
            //Deklarasikan transactionDetails untuk setiap perulangan / looping berdasarkan Transactions Id
            TransactionDetails details = detailsService.getDetails(tr.getId());

            TransactionDetailsResponse data = TransactionDetailsResponse.builder()
                    .transactionDate(tr.getPurchaseDate().toString())
                    .customerId(tr.getCustomer().getId())
                    .customerName(tr.getCustomer().getName())
                    .productId(details.getProducts().getId())
                    .productName(details.getProducts().getName())
                    .productCode(details.getProducts().getCode())
                    .salesPrice(details.getSalesPrice())
                    .build();

            dto.add(data);
        }

        ControllerResponse<List<TransactionDetailsResponse>> response = ControllerResponse.<List<TransactionDetailsResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(dto)
                .build();

        return response;
    }
}
