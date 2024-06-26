package com.majumundur.Services;

import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Models.DTO.Requests.RewardsCreateRequest;
import com.majumundur.Models.DTO.Responses.RewardResponse;
import com.majumundur.Models.Rewards;
import com.majumundur.Repositories.RewardsRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RewardsServiceImpl implements RewardsService {
    private RewardsRepository repository;
    private ValidationService validation;

    public RewardsServiceImpl(RewardsRepository repository, ValidationService validation){
        this.repository = repository;
        this.validation = validation;
    }

    @PostConstruct
    public void initializeRewards(){
        Rewards rewardA = Rewards.builder()
                .code("A")
                .name("Reward A")
                .point(20)
                .build();
        Rewards rewardB = Rewards.builder()
                .code("B")
                .name("Reward B")
                .point(40)
                .build();
        if(repository.findByCode(rewardA.getCode()) != null || repository.findByCode(rewardB.getCode()) != null){
            return;
        }
        repository.save(rewardA);
        repository.save(rewardB);
    }
    @Override
    public ControllerResponse<List<RewardResponse>> getAll() {
        List<Rewards> rewards = repository.findAll();
        List<RewardResponse> dto = new ArrayList<>();
        ControllerResponse<List<RewardResponse>> response = new ControllerResponse<>();

        for(Rewards r : rewards){
            RewardResponse rewardsResponse = new RewardResponse();
            rewardsResponse.setRewardsId(r.getId());
            rewardsResponse.setRewardsCode(r.getCode());
            rewardsResponse.setRewardsName(r.getName());
            rewardsResponse.setRequiredPoints(r.getPoint());

            dto.add(rewardsResponse);
        }
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setData(dto);

        return response;
    }

    //Penambahan Annotasi @Transactional Untuk Menandakan Bahwa Ini Process Transaction Pada Database
    @Transactional
    @Override
    public ControllerResponse<?> create(RewardsCreateRequest request){
        try{
            List<String> violations = validation.validate(request);
            if(violations != null) {
                ControllerResponse<List<String>> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
                response.setData(violations);
                return response;
            }
            if(repository.findByCode(request.getRewardsCode()) != null){
                ControllerResponse<List<String>> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Reward Already Exists");
                return response;
            }
            //Contoh pemanfaatan Builder milik Lombok
            Rewards reward = Rewards.builder()
                    .code(request.getRewardsCode())
                    .name(request.getRewardsName())
                    .point(request.getRequiredPoints())
                    .build();

            repository.save(reward);

            RewardResponse rewardsResponse = RewardResponse.builder()
                    .rewardsId(reward.getId())
                    .rewardsCode(reward.getCode())
                    .rewardsName(reward.getName())
                    .requiredPoints(reward.getPoint())
                    .build();

            ControllerResponse<RewardResponse> response = ControllerResponse.<RewardResponse>builder()
                    .statusCode(HttpStatus.CREATED.value())
                    .message(HttpStatus.CREATED.getReasonPhrase())
                    .data(rewardsResponse)
                    .build();
            return response;
        }
        catch (Exception e){
            ControllerResponse<String> response = new ControllerResponse<>();
            response.setStatusCode(HttpStatus.FORBIDDEN.value());
            response.setMessage(HttpStatus.FORBIDDEN.getReasonPhrase());
            response.setData(e.getMessage());
            return response;
        }
    }

    @Override
    public Rewards getReward(String id) {
        Optional<Rewards> reward = repository.findById(id);
        if(reward.isEmpty()){
            return null;
        }
        return reward.get();
    }
}
