package com.restaurant.food.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignupRequestDto {
    /*
     * 회원가입 요청 DTO
     */
    private String email;
    private String password;
    private String nickname;
}
