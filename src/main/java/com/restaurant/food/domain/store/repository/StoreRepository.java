package com.restaurant.food.domain.store.repository;

import com.restaurant.food.domain.store.entity.Category;
import com.restaurant.food.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByCategory(Category category);
    List<Store> findAllByUserId(Long userId);
}
