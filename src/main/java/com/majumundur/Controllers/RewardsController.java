package com.majumundur.Controllers;

import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Models.DTO.Requests.RewardsCreateRequest;
import com.majumundur.Services.RewardTransactionsService;
import com.majumundur.Services.RewardsService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Rewards Controller", description = "Controller To Upload New Rewards, View All Rewards, Search Rewards By It's Code / Name")
@RequestMapping("/api/rewards")
public class RewardsController {
    private RewardsService service;
    private RewardTransactionsService transactionsService;

    public RewardsController(RewardsService service, RewardTransactionsService transactionsService) {
        this.service = service;
        this.transactionsService = transactionsService;
    }

    @Operation(summary = "View All Rewards" , description = "Get All Reward's Stored Data")
    @GetMapping
    public ResponseEntity<ControllerResponse> getAllRewards(){
        ControllerResponse<?> response = service.getAll();
        return ResponseEntity.ok(response);
    }

    //Hidden Karena Tidak Relevan Dalam Alur Bisnis,
    //Namun Bisa mempermudah untuk membuat Reward Baru
    @Hidden
    @Operation(summary = "Create New Reward " , description = "Create Reward, SUPER_ADMIN ONLY !!")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> createNewReward(@RequestBody RewardsCreateRequest request){
            ControllerResponse<?> response = service.create(request);
            return  ResponseEntity.ok(response);
    }

    @Operation(summary = "Redeem A Reward" , description = "Redeem A Rewards Based On Available Rewards")
    @PreAuthorize("hasAnyRole('CUSTOMER','SUPER_ADMIN')")
    @PostMapping("/redeem")
    public ResponseEntity<ControllerResponse> redeemReward(@RequestParam String customerId,
                                                           @RequestParam String rewardId){
        ControllerResponse<?> response = transactionsService.redeemReward(customerId,rewardId);
        return ResponseEntity.ok(response);
    }

}
