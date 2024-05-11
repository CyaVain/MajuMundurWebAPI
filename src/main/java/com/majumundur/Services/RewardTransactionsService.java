package com.majumundur.Services;

import com.majumundur.Models.DTO.Responses.ControllerResponse;

public interface RewardTransactionsService {

    public ControllerResponse<?> redeemReward(String customerId, String rewardsId);

    public ControllerResponse<?> redeemHistories();
}
