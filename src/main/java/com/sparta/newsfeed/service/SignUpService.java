package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dtos.signup.SignUpRequestDto;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class SignUpService {

    private final UserRepository userRepository;

    @Autowired
    public SignUpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 유저 회원가입 메서드
    public User addUser(SignUpRequestDto requestDto) {
        User user = new User();

        user.setUser_id(requestDto.getUserId());
        user.setPassword(requestDto.getPassword());
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setOne_liner(requestDto.getOne_liner());

        return userRepository.save(user);
    }

    // 유저 로그인 메서드
    public boolean loginUser(SignUpRequestDto requestDto) {
        User user = userRepository.findByUserId(requestDto.getUserId());

        if (!user.getUserId().equals(requestDto.getUserId())) {
            throw new IllegalArgumentException("존재하지 않는 아이디 입니다");
        }
        if (!user.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        return true;
    }
}
