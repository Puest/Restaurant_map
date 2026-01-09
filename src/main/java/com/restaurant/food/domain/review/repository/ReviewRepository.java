package com.restaurant.food.domain.review.repository;

import com.restaurant.food.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByStoreId(Long storeId);
}
