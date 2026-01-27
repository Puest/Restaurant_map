package com.restaurant.food.domain.store.entity;

import com.restaurant.food.domain.review.entity.Review;
import com.restaurant.food.domain.user.entity.User;
import com.restaurant.food.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Store extends BaseTimeEntity {
    /* 가게 엔티티
     * 맛집은 한 명의 등록자가 있으며, 여러 개의 리뷰를 가집니다. */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; //가게 명
    private String address; // 주소

    @Convert(converter = CategoryConverter.class)
    private Category category; // 종류
    private Double latitude; // 위도
    private Double longitude; // 경도

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 등록한 사용자

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public void update(String name, String address, Category category, Double latitude, Double longitude) {
        this.name = name;
        this.address = address;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
