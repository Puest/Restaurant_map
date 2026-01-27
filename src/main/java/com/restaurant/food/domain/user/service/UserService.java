package com.restaurant.food.domain.user.service;

import com.restaurant.food.domain.user.dto.TokenInfo;
import com.restaurant.food.domain.user.dto.UserLoginRequestDto;
import com.restaurant.food.domain.user.dto.UserResponseDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

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
     *   내 정보 조회
     */
    public UserResponseDto getMyInfo(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return new UserResponseDto(user);
    }

    /*
     *   프로필 수정 (자기소개)
     */
    @Transactional
    public UserResponseDto updateProfile(String email, String bio) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.updateBio(bio);
        return new UserResponseDto(user);
    }

    /*
     *   프로필 이미지 업로드
     */
    @Transactional
    public UserResponseDto updateProfileImage(String email, MultipartFile file) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        try {
            Path uploadDir = Paths.get("uploads/profiles");
            Files.createDirectories(uploadDir);

            // 기존 이미지 삭제
            if (user.getProfileImage() != null) {
                Files.deleteIfExists(uploadDir.resolve(user.getProfileImage()));
            }

            // 새 이미지 저장
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), uploadDir.resolve(filename), StandardCopyOption.REPLACE_EXISTING);

            user.updateProfileImage(filename);
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드에 실패했습니다.", e);
        }

        return new UserResponseDto(user);
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
