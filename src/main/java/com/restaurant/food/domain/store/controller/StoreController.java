package com.restaurant.food.domain.store.controller;

import com.restaurant.food.domain.store.dto.StoreRequestDto;
import com.restaurant.food.domain.store.dto.StoreResponseDto;
import com.restaurant.food.domain.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * 맛집 등록 API
     * @param storeRequestDto 등록 정보 (이름, 주소, 카테고리)
     * @return 생성된 맛집의 ID
     */
    @PostMapping
    public ResponseEntity<Long> createStore(@Valid @RequestBody StoreRequestDto storeRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        // 서비스 호출 및 저장
        Long storeId = storeService.saveStore(storeRequestDto, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(storeId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreResponseDto> getStore(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.getStore(id));
    }

    @GetMapping
    public ResponseEntity<List<StoreResponseDto>> getAllStores() {
        return ResponseEntity.ok(storeService.getAllStores());
    }

    @GetMapping("/my")
    public ResponseEntity<List<StoreResponseDto>> getMyStores(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(storeService.getMyStores(userDetails.getUsername()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreResponseDto> updateStore(
            @PathVariable Long id,
            @Valid @RequestBody StoreRequestDto storeRequestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(storeService.updateStore(id, storeRequestDto, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        storeService.deleteStore(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
