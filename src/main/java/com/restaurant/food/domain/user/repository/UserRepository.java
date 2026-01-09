package com.restaurant.food.domain.user.repository;

import com.restaurant.food.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일로 회원을 찾거나 중복 가입 확인을 위한 메서드 추가
    Optional<User> findByEmail(String email); // 로그인용
    boolean existsByEmail(String email); // 중복 가입 방지

}
