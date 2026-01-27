package com.restaurant.food.domain.user.controller;

import com.restaurant.food.domain.user.dto.TokenInfo;
import com.restaurant.food.domain.user.dto.UserLoginRequestDto;
import com.restaurant.food.domain.user.dto.UserResponseDto;
import com.restaurant.food.domain.user.dto.UserSignupRequestDto;
import com.restaurant.food.domain.user.dto.UserUpdateRequestDto;
import com.restaurant.food.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody UserSignupRequestDto requestDto) {
        return ResponseEntity.ok(userService.signUp(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenInfo> login(@RequestBody UserLoginRequestDto requestDto) {
        return ResponseEntity.ok(userService.login(requestDto));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getMyInfo(userDetails.getUsername()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UserUpdateRequestDto requestDto) {
        return ResponseEntity.ok(userService.updateProfile(userDetails.getUsername(), requestDto.getBio()));
    }

    @PostMapping("/me/image")
    public ResponseEntity<UserResponseDto> updateProfileImage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("image") MultipartFile file) {
        return ResponseEntity.ok(userService.updateProfileImage(userDetails.getUsername(), file));
    }
}
