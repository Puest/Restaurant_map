package com.restaurant.food.domain.review.dto;

import com.restaurant.food.domain.review.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyReviewResponseDto {
    private Long id;
    private Long storeId;
    private String storeName;
    private String comment;
    private Integer rating;

    public MyReviewResponseDto(Review review) {
        this.id = review.getId();
        this.storeId = review.getStore().getId();
        this.storeName = review.getStore().getName();
        this.comment = review.getComment();
        this.rating = review.getRating();
    }
}
