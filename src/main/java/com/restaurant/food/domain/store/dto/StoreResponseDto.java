package com.restaurant.food.domain.store.dto;

import com.restaurant.food.domain.store.entity.Store;
import jakarta.validation.constraints.NotBlank;
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

    // Entity → DTO 변환 생성자
    public StoreResponseDto(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.address = store.getAddress();
        this.category = store.getCategory();
        this.latitude = store.getLatitude();
        this.longitude = store.getLongitude();
    }
}

