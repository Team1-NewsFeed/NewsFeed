package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dtos.signup.SignUpRequestDto;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.jwt.util.JwtTokenProvider;
import com.sparta.newsfeed.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Getter
public class SignUpService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public SignUpService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 유저 회원가입 메서드
    public String addUser(SignUpRequestDto requestDto) {
        User user = new User();

        user.setUser_id(requestDto.getUserId());
        // 입력받은 비밀번호 암호화.
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setOne_liner(requestDto.getOne_liner());

        userRepository.save(user);
        // 회원가입시 JWT 토큰 생성하는 로직
        return JwtTokenProvider.generateToken(user.getUserId());
    }

    // 유저 로그인 메서드

}
