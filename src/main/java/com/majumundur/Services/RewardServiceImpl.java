package com.majumundur.Services;

import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Models.DTO.Requests.RewardsCreateRequest;
import com.majumundur.Models.DTO.Responses.RewardsResponse;
import com.majumundur.Models.Rewards;
import com.majumundur.Repositories.RewardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RewardServiceImpl implements  RewardService{
    private RewardRepository repository;
    private ValidationService validation;

    public RewardServiceImpl(RewardRepository repository, ValidationService validation){
        this.repository = repository;
        this.validation = validation;
    }
    @Override
    public ControllerResponse<List<RewardsResponse>> getAll() {
        List<Rewards> rewards = repository.findAll();
        List<RewardsResponse> dto = new ArrayList<>();
        ControllerResponse<List<RewardsResponse>> response = new ControllerResponse<>();

        for(Rewards r : rewards){
            RewardsResponse rewardsResponse = new RewardsResponse();
            rewardsResponse.setRewardsCode(r.getCode());
            rewardsResponse.setRewardsName(r.getName());
            rewardsResponse.setRewardsPoint(r.getPoint());

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

            List<String> violations = validation.validate(request);
            if(violations != null) {
                ControllerResponse<List<String>> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
                response.setData(violations);
                return response;
            }
        try{
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

            RewardsResponse rewardsResponse = RewardsResponse.builder()
                    .rewardsCode(reward.getCode())
                    .rewardsName(reward.getName())
                    .rewardsPoint(reward.getPoint())
                    .build();

            ControllerResponse<RewardsResponse> response = ControllerResponse.<RewardsResponse>builder()
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
}
