package com.majumundur.Models.DTO.Responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RewardResponse {
    private String rewardsId;
    private String rewardsCode;
    private String rewardsName;
    private Integer requiredPoints;
}
