package com.restaurant.food.domain.favorite.service;

import com.restaurant.food.domain.favorite.dto.FavoriteResponseDto;
import com.restaurant.food.domain.favorite.entity.Favorite;
import com.restaurant.food.domain.favorite.repository.FavoriteRepository;
import com.restaurant.food.domain.store.dto.StoreResponseDto;
import com.restaurant.food.domain.store.entity.Store;
import com.restaurant.food.domain.store.repository.StoreRepository;
import com.restaurant.food.domain.user.entity.User;
import com.restaurant.food.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public FavoriteResponseDto toggleFavorite(Long storeId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다."));

        Optional<Favorite> existing = favoriteRepository.findByUserIdAndStoreId(user.getId(), store.getId());

        if (existing.isPresent()) {
            favoriteRepository.delete(existing.get());
            return new FavoriteResponseDto(storeId, false);
        } else {
            Favorite favorite = Favorite.builder()
                    .user(user)
                    .store(store)
                    .build();
            favoriteRepository.save(favorite);
            return new FavoriteResponseDto(storeId, true);
        }
    }

    @Transactional(readOnly = true)
    public List<Long> getMyFavoriteStoreIds(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return favoriteRepository.findAllByUserId(user.getId()).stream()
                .map(fav -> fav.getStore().getId())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean isFavorited(Long storeId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return favoriteRepository.existsByUserIdAndStoreId(user.getId(), storeId);
    }

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getMyFavoriteStores(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return favoriteRepository.findAllByUserId(user.getId()).stream()
                .map(fav -> new StoreResponseDto(fav.getStore()))
                .collect(Collectors.toList());
    }
}
