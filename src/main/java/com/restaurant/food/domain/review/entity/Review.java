package com.restaurant.food.domain.review.entity;

import com.restaurant.food.domain.store.entity.Store;
import com.restaurant.food.domain.user.entity.User;
import com.restaurant.food.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseTimeEntity {
    /* 리뷰 엔티티
     * 사용자가 가게에 남긴 것을 확인할 수 있습니다.*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment; // 리뷰
    private Integer rating; // 별점

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;


}
