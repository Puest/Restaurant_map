package com.restaurant.food.domain.favorite.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FavoriteResponseDto {
    private Long storeId;
    private boolean favorited;
}
