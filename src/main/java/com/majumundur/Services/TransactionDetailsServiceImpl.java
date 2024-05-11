package com.majumundur.Services;

import com.majumundur.Models.TransactionDetails;
import com.majumundur.Models.Transactions;
import com.majumundur.Repositories.TransactionDetailsRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionDetailsServiceImpl implements TransactionDetailsService {

    private TransactionDetailsRepository repository;

    public TransactionDetailsServiceImpl(TransactionDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveDetails(Transactions transaction, TransactionDetails details) {
        TransactionDetails detail = TransactionDetails.builder()
                .id(details.getId())
                .transaction(transaction)
                .products(details.getProducts())
                .salesPrice(details.getSalesPrice())
                .build();
        repository.save(detail);
    }

    @Override
    public TransactionDetails getDetails(String transactionId) {
        TransactionDetails details = repository.findByTransaction_Id(transactionId);
        if(details == null){
            return null;
        }
        return details;
    }
}
