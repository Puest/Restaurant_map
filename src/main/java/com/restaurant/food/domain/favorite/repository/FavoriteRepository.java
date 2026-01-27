package com.restaurant.food.domain.favorite.repository;

import com.restaurant.food.domain.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByUserIdAndStoreId(Long userId, Long storeId);

    boolean existsByUserIdAndStoreId(Long userId, Long storeId);

    List<Favorite> findAllByUserId(Long userId);

    void deleteAllByStoreId(Long storeId);
}
