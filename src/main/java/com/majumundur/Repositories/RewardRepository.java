package com.majumundur.Repositories;

import com.majumundur.Models.Rewards;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository  extends JpaRepository<Rewards, String> {
}
