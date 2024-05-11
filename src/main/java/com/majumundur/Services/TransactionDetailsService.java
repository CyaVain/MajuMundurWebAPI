package com.majumundur.Services;

import com.majumundur.Models.DTO.Responses.PagingResponse;
import com.majumundur.Models.TransactionDetails;
import com.majumundur.Models.Transactions;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionDetailsService {

    void saveDetails(Transactions transaction, TransactionDetails details);

    TransactionDetails getDetails(String transactionId);
}
