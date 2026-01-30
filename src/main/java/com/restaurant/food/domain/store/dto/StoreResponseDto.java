package com.restaurant.food.domain.store.dto;

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
    private Double myRating; // 나의 별점
    private String myNote; // 나의 기록
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
        this.myRating = store.getMyRating();
        this.myNote = store.getMyNote();
    }
}
