package com.majumundur.Services;

import com.majumundur.Models.DTO.ControllerResponse;
import com.majumundur.Models.DTO.RewardsCreateRequest;
import com.majumundur.Models.DTO.RewardsResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RewardService {

    public ControllerResponse<List<RewardsResponse>> GetAll();

    //Penggunaan WildCard (?) untuk menghandle agar ControllerResponse dapat menerima Object Lain yang lebih fleksibel
    public ControllerResponse<?>Create(RewardsCreateRequest request);
}
