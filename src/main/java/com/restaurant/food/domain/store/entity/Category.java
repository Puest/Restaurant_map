package com.restaurant.food.domain.store.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Category {
    KOREAN("korean", "한식"),
    CHINESE("chinese", "중식"),
    JAPANESE("japanese", "일식"),
    WESTERN("western", "양식"),
    CAFE("cafe", "카페/디저트"),
    CHICKEN("chicken", "치킨"),
    PIZZA("pizza", "피자"),
    BUNSIK("bunsik", "분식"),
    MEAT("meat", "고기/구이"),
    SEAFOOD("seafood", "해산물"),
    FASTFOOD("fastfood", "패스트푸드"),
    OTHER("other", "기타");

    private final String id;
    private final String displayName;

    Category(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    @JsonValue
    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static Category fromId(String id) {
        for (Category category : values()) {
            if (category.id.equals(id)) {
                return category;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 카테고리: " + id);
    }
}
