package com.majumundur.Services;

import com.majumundur.Models.Customers;
import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Models.DTO.Responses.RewardRedeemedResponse;
import com.majumundur.Models.RewardTransactions;
import com.majumundur.Models.Rewards;
import com.majumundur.Repositories.RewardTransactionsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Service
public class RewardTransactionsServiceImpl implements RewardTransactionsService {

    private RewardTransactionsRepository repository;
    private RewardsService rewardsService;
    private CustomersService customersService;

    public RewardTransactionsServiceImpl(RewardTransactionsRepository repository, RewardsService rewardsService, CustomersService customersService) {
        this.repository = repository;
        this.rewardsService = rewardsService;
        this.customersService = customersService;
    }

    @Transactional
    @Override
    public ControllerResponse<?> redeemReward(String customerId, String rewardsId) {
        try {
            Customers customer = customersService.getCustomer(customerId);
            if(customer == null){
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setData("Customer Not Found / Invalid Customer Id");
                return response;
            }

            Rewards rewards = rewardsService.getReward(rewardsId);
            if(rewards == null){
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setData("Reward Not Found / Invalid Reward Id");
                return response;
            }

            if(customer.getRewardPoints() < rewards.getPoint()){
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                response.setMessage(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
                response.setData("Insufficient Customer's Points / Customer's Points Is Too Low To Redeem This Reward");
                return response;
            }

            Integer point = rewards.getPoint();
            customersService.reducePoint(customer, point);

            RewardTransactions transaction = RewardTransactions.builder()
                    .purchaseDate(Date.from(Instant.now()))
                    .customer(customer)
                    .reward(rewards)
                    .build();
            repository.save(transaction);

            RewardRedeemedResponse dto = RewardRedeemedResponse.builder()
                    .purchaseDate(transaction.getPurchaseDate().toString())
                    .pointSpent(transaction.getReward().getPoint())
                    .customerId(transaction.getCustomer().getId())
                    .customerName(transaction.getCustomer().getName())
                    .customerCurrentPoints(transaction.getCustomer().getRewardPoints())
                    .rewardId(transaction.getReward().getId())
                    .rewardName(transaction.getReward().getName())
                    .build();

            ControllerResponse<RewardRedeemedResponse> response = ControllerResponse.<RewardRedeemedResponse>builder()
                    .statusCode(HttpStatus.CREATED.value())
                    .message(HttpStatus.CREATED.getReasonPhrase())
                    .data(dto)
                    .build();

            return response;
        }
        catch (Exception e){
            ControllerResponse<String> response = new ControllerResponse<>();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response.setData(e.getMessage());
            return  response;
        }
    }

    @Override
    public ControllerResponse<?> redeemHistories() {
        return null;
    }
}
