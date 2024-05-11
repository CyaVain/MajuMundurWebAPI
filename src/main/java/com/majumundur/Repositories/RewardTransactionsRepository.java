package com.majumundur.Repositories;

import com.majumundur.Models.RewardTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RewardTransactionsRepository extends JpaRepository<RewardTransactions, String> , JpaSpecificationExecutor<RewardTransactions> {
}
