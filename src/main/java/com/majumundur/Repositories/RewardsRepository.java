package com.majumundur.Repositories;

import com.majumundur.Models.Rewards;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardsRepository extends JpaRepository<Rewards, String> {

    Rewards findByCode(String code);
}
