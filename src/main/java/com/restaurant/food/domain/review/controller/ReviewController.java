package com.restaurant.food.domain.review.controller;

import com.restaurant.food.domain.review.dto.ReviewRequestDto;
import com.restaurant.food.domain.review.dto.ReviewResponseDto;
import com.restaurant.food.domain.review.repository.ReviewRepository;
import com.restaurant.food.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Long> createView(@Valid @RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
        Long reviewId = reviewService.createReview(requestDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewId);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<List<ReviewResponseDto>> getStoreReview(@PathVariable Long storeId) {
        return ResponseEntity.ok(reviewService.getReviews(storeId));
    }
}
