package com.majumundur.Models.DTO.Responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RewardRedeemedResponse {
    private String purchaseDate;
    private Integer pointSpent;
    private String customerId;
    private String customerName;
    private Integer customerCurrentPoints;
    private String rewardId;
    private String rewardName;
}
