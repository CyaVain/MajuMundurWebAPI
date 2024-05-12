package com.majumundur.Controllers;

import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Models.DTO.Requests.RewardsCreateRequest;
import com.majumundur.Services.RewardTransactionsService;
import com.majumundur.Services.RewardsService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Rewards Controller", description = "Controller To Create New Rewards, View All Rewards, Redeem Rewards")
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

    @Operation(summary = "Create New Reward SUPER_ADMIN ONLY" , description = "Create Reward, SUPER_ADMIN ONLY !!")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> createNewReward(@RequestBody RewardsCreateRequest request){
            ControllerResponse<?> response = service.create(request);
            return  ResponseEntity.ok(response);
    }

    @Operation(summary = "Redeem A Reward CUSTOMER OR SUPER_ADMIN ONLY" , description = "Redeem A Rewards Based On Available Rewards, CUSTOMER OR SUPER_ADMIN ONLY")
    @PreAuthorize("hasAnyRole('CUSTOMER','SUPER_ADMIN')")
    @PostMapping("/redeem")
    public ResponseEntity<ControllerResponse> redeemReward(@RequestParam String customerId,
                                                           @RequestParam String rewardId){
        ControllerResponse<?> response = transactionsService.redeemReward(customerId,rewardId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get Redeemed Rewards Histories CUSTOMER OR SUPER_ADMIN ONLY " , description = "Get Redeemed Rewards Histories, CUSTOMER OR SUPER_ADMIN ONLY")
    @PreAuthorize("hasAnyRole('CUSTOMER','SUPER_ADMIN')")
    @GetMapping("/history")
    public ResponseEntity<ControllerResponse> getHistories(@RequestParam(name = "page",required = false,defaultValue = "0") Integer page,
                                                           @RequestParam(name = "size",required = false,defaultValue = "5") Integer size){
        Pageable pageable = PageRequest.of(page,size);
        ControllerResponse<?> response = transactionsService.redeemHistories(pageable);
        return ResponseEntity.ok(response);
    }

}
