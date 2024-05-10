package com.majumundur.Services;

import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Models.DTO.Requests.RewardsCreateRequest;
import com.majumundur.Models.DTO.Responses.RewardsResponse;

import java.util.List;

public interface RewardService {

    public ControllerResponse<List<RewardsResponse>> getAll();

    //Penggunaan WildCard (?) untuk menghandle agar ControllerResponse dapat menerima Object Lain yang lebih fleksibel
    public ControllerResponse<?> create(RewardsCreateRequest request);
}
