package com.restaurant.food.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TokenInfo {
    /*
     * 토큰 응답 DTO
     */
    private String grantType;
    private String accessToken;
}


