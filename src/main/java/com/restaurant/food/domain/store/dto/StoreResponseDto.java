package com.restaurant.food.domain.store.dto;

import com.restaurant.food.domain.review.entity.Review;
import com.restaurant.food.domain.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreResponseDto {
    private Long id;
    private String name;
    private String address;
    private String category;
    private Double latitude;
    private Double longitude;
    private Double avgRating;
    private Long userId;

    // Entity → DTO 변환 생성자
    public StoreResponseDto(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.address = store.getAddress();
        this.category = store.getCategory().getId();
        this.latitude = store.getLatitude();
        this.longitude = store.getLongitude();
        this.userId = store.getUser().getId();

        // 별점 평균 계산 로직
        double sum = 0.0;
        int count = 0;

        for (Review review : store.getReviews()) {
            sum += review.getRating();
            count++;
        }

        this.avgRating = (count == 0) ? 0.0 : sum / count;
    }
}
