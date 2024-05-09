package com.majumundur.Models.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RewardsResponse {
    private String rewardsCode;
    private String rewardsName;
    private Integer rewardsPoint;
}
