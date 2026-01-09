package com.restaurant.food.global.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class KakaoApiResponseDto {
    private List<Document> documents;
    
    @Getter
    @NoArgsConstructor
    public static class Document{
        @JsonProperty("x")
        private String longitude; // 위도
        @JsonProperty("y")
        private String latitude; // 경도
    }
}
