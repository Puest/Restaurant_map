package com.restaurant.food.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    @NotNull(message = "가게 ID는 필수입니다.")
    private Long storeId;

    @NotNull(message = "리뷰 내용은 필수입니다.")
    private String comment;

    @Min(value = 1, message = "별점은 최소 1점 이상이어야 합니다.")
    @Max(value = 5, message = "별점은 최대 5점 이하여야 합니다.")
    private Integer rating;
}
