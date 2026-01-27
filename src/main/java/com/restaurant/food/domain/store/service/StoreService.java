package com.restaurant.food.domain.store.service;

import com.restaurant.food.domain.store.dto.StoreRequestDto;
import com.restaurant.food.domain.store.dto.StoreResponseDto;
import com.restaurant.food.domain.store.entity.Store;
import com.restaurant.food.domain.store.repository.StoreRepository;
import com.restaurant.food.domain.user.entity.User;
import com.restaurant.food.domain.user.repository.UserRepository;
import com.restaurant.food.global.service.KakaoAddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {
    private final StoreRepository storeRepository;
    private final KakaoAddressService kakaoAddressService;
    private final UserRepository userRepository;

    public Long saveStore(StoreRequestDto request, String userEmail) {
        // 0. 유저 조회 (로그인 사용자 확인)
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

        // 1. 외부 API를 통해 위도/경도 획득
        KakaoAddressService.Coordinate coordinate = kakaoAddressService.getCoordinate(request.getAddress());

        // 2. Entity 생성 및 저장
        Store store = Store.builder()
                .name(request.getName())
                .address(request.getAddress())
                .category(request.getCategory())
                .latitude(coordinate.getLat())
                .longitude(coordinate.getLng())
                .user(user)
                .build();

        return storeRepository.save(store).getId();
    }

    // 1. 단건 조회
    public StoreResponseDto getStore(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 맛집이 존재하지 않습니다. ID = " + id));

        return new StoreResponseDto(store);
    }

    // 2. 전체 목록 조회
    public List<StoreResponseDto> getAllStores() {
        return storeRepository.findAll().stream()
                .map(StoreResponseDto::new)
                .collect(Collectors.toList());
    }

    // 3. 내가 등록한 맛집 조회
    public List<StoreResponseDto> getMyStores(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

        return storeRepository.findAllByUserId(user.getId()).stream()
                .map(StoreResponseDto::new)
                .collect(Collectors.toList());
    }
}
