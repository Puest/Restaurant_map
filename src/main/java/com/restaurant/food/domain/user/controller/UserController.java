package com.restaurant.food.domain.user.controller;

import com.restaurant.food.domain.user.dto.TokenInfo;
import com.restaurant.food.domain.user.dto.UserLoginRequestDto;
import com.restaurant.food.domain.user.dto.UserSignupRequestDto;
import com.restaurant.food.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
