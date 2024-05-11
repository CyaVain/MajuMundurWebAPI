package com.majumundur.Services;

import com.majumundur.Models.DTO.Requests.TransactionCreateRequest;
import com.majumundur.Models.DTO.Responses.ControllerResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionsService {

    public ControllerResponse<?> createTransaction(TransactionCreateRequest request);
    public ControllerResponse<?> getTransactionHistory(String transactionId);

    public ControllerResponse<?> getCustomerHistories();
}
