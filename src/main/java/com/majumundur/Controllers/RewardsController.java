package com.majumundur.Controllers;

import com.majumundur.Models.DTO.ControllerResponse;
import com.majumundur.Models.DTO.RewardsCreateRequest;
import com.majumundur.Services.RewardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Rewards Controller", description = "Controller To Upload New Rewards, View All Rewards, Search Rewards By It's Code / Name")
@RequestMapping("/api/rewards")
public class RewardsController {
    private RewardService service;
    public RewardsController(RewardService service){
        this.service = service;
    }

    @Operation(summary = "View All Rewards" , description = "Get All Reward's Stored Data")
    @GetMapping
    public ResponseEntity<ControllerResponse> GetAllRewards(){
        ControllerResponse<?> response = service.GetAll();
        return ResponseEntity.ok(response);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ControllerResponse> CreateNewReward(@RequestBody RewardsCreateRequest request){
        return  null;
    }
}
