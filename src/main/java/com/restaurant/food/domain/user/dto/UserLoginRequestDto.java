package com.restaurant.food.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequestDto {

    /* 로그인 요청 DTO */
    private String email;
    private String password;
}
