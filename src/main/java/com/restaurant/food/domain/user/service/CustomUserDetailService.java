package com.restaurant.food.domain.user.service;

import com.restaurant.food.domain.user.entity.User;
import com.restaurant.food.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    /*
     * DB에서 유저 정보를 가져오는 방법 구현
     */
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다.");
        }

        User user = optionalUser.get();

        return createUserDetails(user);
    }

    // DB 의 User 정보를 기반으로 Spring Security 의 User 객체 생성
    private UserDetails createUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

}
