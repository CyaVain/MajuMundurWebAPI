package com.majumundur.Controllers;

import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Models.DTO.Requests.RewardsCreateRequest;
import com.majumundur.Services.RewardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.annotation.Secured;

@RestController
@Tag(name = "Rewards Controller", description = "Controller To Upload New Rewards, View All Rewards, Search Rewards By It's Code / Name")
@RequestMapping("/api/rewards")
public class RewardsController {
    private RewardService service;
    public RewardsController(RewardService service){
        this.service = service;
    }

    @Operation(summary = "View All Rewards" , description = "Get All Reward's Stored Data")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ControllerResponse> GetAllRewards(){
        ControllerResponse<?> response = service.GetAll();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create New Reward " , description = "Create Reward, SUPER_ADMIN ONLY !!")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> CreateNewReward(@RequestBody RewardsCreateRequest request){
            ControllerResponse<?> response = service.Create(request);
            return  ResponseEntity.ok(response);
    }
}
