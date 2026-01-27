package com.restaurant.food.domain.user.dto;

import com.restaurant.food.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String bio;
    private String profileImage;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.bio = user.getBio();
        this.profileImage = user.getProfileImage() != null
                ? "/uploads/profiles/" + user.getProfileImage()
                : null;
    }
}
