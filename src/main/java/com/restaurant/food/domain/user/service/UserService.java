package com.restaurant.food.domain.user.service;

import com.restaurant.food.domain.user.dto.TokenInfo;
import com.restaurant.food.domain.user.dto.UserLoginRequestDto;
import com.restaurant.food.domain.user.dto.UserSignupRequestDto;
import com.restaurant.food.domain.user.entity.User;
import com.restaurant.food.domain.user.repository.UserRepository;
import com.restaurant.food.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    /*
     *   회원 가입 로직
     */
    @Transactional
    public Long signUp(UserSignupRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        String encodePassword = passwordEncoder.encode(requestDto.getPassword());

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(encodePassword)
                .nickname(requestDto.getNickname())
                .build();

        return userRepository.save(user).getId();
    }

    /*
     *   로그인 로직
     */
    public TokenInfo login(UserLoginRequestDto requestDto) {
        // Login ID/PW를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());

        // 실제 검증
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보를 기반으로 JWT 생성
        String accessToken = jwtTokenProvider.createToken(authentication);

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();

    }

}
