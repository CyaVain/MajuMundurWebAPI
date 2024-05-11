package com.majumundur.Repositories;

import com.majumundur.Models.TransactionDetails;
import com.majumundur.Models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

//JpaSpecificationExector Untuk menerapkan Specification Untuk Class TransactionDetails
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, String>, JpaSpecificationExecutor<TransactionDetails> {

    TransactionDetails findByTransaction_Id(String transactionId);
}
