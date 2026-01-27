package com.restaurant.food.domain.store.dto;

import com.restaurant.food.domain.store.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class StoreRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private Category category;
}
