package com.majumundur.Services;

import com.majumundur.Models.DTO.Responses.ControllerResponse;
import org.springframework.data.domain.Pageable;

public interface RewardTransactionsService {

    public ControllerResponse<?> redeemReward(String customerId, String rewardsId);

    public ControllerResponse<?> redeemHistories(Pageable pageable);
}
