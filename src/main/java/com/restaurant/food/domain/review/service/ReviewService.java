package com.restaurant.food.domain.review.service;

import com.restaurant.food.domain.review.dto.MyReviewResponseDto;
import com.restaurant.food.domain.review.dto.ReviewRequestDto;
import com.restaurant.food.domain.review.dto.ReviewResponseDto;
import com.restaurant.food.domain.review.entity.Review;
import com.restaurant.food.domain.review.repository.ReviewRepository;
import com.restaurant.food.domain.store.entity.Store;
import com.restaurant.food.domain.store.repository.StoreRepository;
import com.restaurant.food.domain.user.entity.User;
import com.restaurant.food.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public Long createReview(ReviewRequestDto requestDto, String email) {
        // 유저 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 가게 조회
        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다."));

        // 리뷰 생성
        Review review = Review.builder()
                .user(user)
                .store(store)
                .rating(requestDto.getRating())
                .comment(requestDto.getComment())
                .build();

        // 저장
        return reviewRepository.save(review).getId();
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviews(Long storeId) {
        // 가게 해당 여부 확인
        storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다."));

        // 해당 가게 리뷰 리스트 조회
        List<Review> reviews = reviewRepository.findAllByStoreId(storeId);

        // Entity -> DTO 변환
        List<ReviewResponseDto> result = new ArrayList<>();

        for(Review review: reviews) {
            result.add(new ReviewResponseDto(review));
        }

        return result;
    }

    @Transactional(readOnly = true)
    public List<MyReviewResponseDto> getMyReviews(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return reviewRepository.findAllByUserId(user.getId()).stream()
                .map(MyReviewResponseDto::new)
                .collect(Collectors.toList());
    }
}
