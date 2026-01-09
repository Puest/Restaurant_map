package com.restaurant.food.domain.store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class StoreRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String category;
}

