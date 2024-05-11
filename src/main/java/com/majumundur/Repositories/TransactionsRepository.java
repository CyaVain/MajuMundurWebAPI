package com.majumundur.Repositories;

import com.majumundur.Models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionsRepository extends JpaRepository<Transactions, String>{
}
