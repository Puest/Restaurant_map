package com.restaurant.food.domain.user.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    @Size(max = 50, message = "자기소개는 50자 이내로 입력해주세요.")
    private String bio;
}
