package com.majumundur.Services;

import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Models.DTO.Requests.RewardsCreateRequest;
import com.majumundur.Models.DTO.Responses.RewardResponse;
import com.majumundur.Models.Rewards;

import java.util.List;

public interface RewardsService {

    public ControllerResponse<List<RewardResponse>> getAll();

    //Penggunaan WildCard (?) untuk menghandle agar ControllerResponse dapat menerima Object Lain yang lebih fleksibel
    public ControllerResponse<?> create(RewardsCreateRequest request);

    public ControllerResponse<?> redeemReward(String rewardsId);

    public Rewards getReward(String rewardsId);
}
