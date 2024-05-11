package com.majumundur.Models.DTO.Responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RewardResponse {
    private String rewardsCode;
    private String rewardsName;
    private Integer requiredPoints;
}
