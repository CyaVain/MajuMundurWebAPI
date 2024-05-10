package com.majumundur.Security.Config;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtClaim {

    private String userId;
    private List<String> roles;

    public String getUserId() {
        return userId;
    }
}
