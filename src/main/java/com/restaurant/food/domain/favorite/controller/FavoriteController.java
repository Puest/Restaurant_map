package com.restaurant.food.domain.favorite.controller;

import com.restaurant.food.domain.favorite.dto.FavoriteResponseDto;
import com.restaurant.food.domain.favorite.service.FavoriteService;
import com.restaurant.food.domain.store.dto.StoreResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{storeId}")
    public ResponseEntity<FavoriteResponseDto> toggleFavorite(
            @PathVariable Long storeId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(favoriteService.toggleFavorite(storeId, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<List<Long>> getMyFavorites(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(favoriteService.getMyFavoriteStoreIds(userDetails.getUsername()));
    }

    @GetMapping("/check/{storeId}")
    public ResponseEntity<FavoriteResponseDto> checkFavorite(
            @PathVariable Long storeId,
            @AuthenticationPrincipal UserDetails userDetails) {
        boolean favorited = favoriteService.isFavorited(storeId, userDetails.getUsername());
        return ResponseEntity.ok(new FavoriteResponseDto(storeId, favorited));
    }

    @GetMapping("/stores")
    public ResponseEntity<List<StoreResponseDto>> getMyFavoriteStores(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(favoriteService.getMyFavoriteStores(userDetails.getUsername()));
    }
}
