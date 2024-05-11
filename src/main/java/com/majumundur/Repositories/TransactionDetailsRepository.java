package com.majumundur.Repositories;

import com.majumundur.Models.TransactionDetails;
import com.majumundur.Models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, String>{

    TransactionDetails findByTransaction_Id(String transactionId);
}
