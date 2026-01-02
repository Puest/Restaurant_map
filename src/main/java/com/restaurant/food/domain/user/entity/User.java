package com.restaurant.food.domain.user.entity;

import com.restaurant.food.domain.review.entity.Review;
import com.restaurant.food.domain.store.entity.Store;
import com.restaurant.food.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {
    /* 사용자 엔티티
    * 사용자는 여러 맛집을 등록하고, 여러 리뷰를 쓸 수 있습니다. */

    // 사용자 인덱스
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 이메일
    @Column(nullable = false ,unique = true)
    private String email;

    // 사용자 비밀번호
    @Column(nullable = false ,unique = true)
    private String password;

    // 사용자 별명
    @Column(nullable = false ,unique = true)
    private String nickname;

    // 맛집 리스트 (양방향)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Store> stores = new ArrayList<>();

    // 리뷰 리스트
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
}

