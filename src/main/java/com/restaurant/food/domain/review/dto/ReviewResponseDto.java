package com.restaurant.food.domain.review.dto;

import com.restaurant.food.domain.review.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {
    private Long id;
    private String nickname;
    private String comment;
    private Integer rating;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.nickname = review.getUser().getNickname();
        this.comment = review.getComment();
        this.rating = review.getRating();
    }
}
