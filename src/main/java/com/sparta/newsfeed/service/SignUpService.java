package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dtos.signup.SignUpRequestDto;
import com.sparta.newsfeed.entity.Signup;
import com.sparta.newsfeed.repository.SignUpRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Getter
public class SignUpService {

    private final SignUpRepository signUpRepository;

    public SignUpService(SignUpRepository signUpRepository) {
        this.signUpRepository = signUpRepository;
    }

    // 유저 회원가입 메서드
    public Signup addUser(SignUpRequestDto requestDto) {
        Signup signup = new Signup();

        signup.setUser_id(requestDto.getUser_id());
        signup.setPassword(requestDto.getPassword());
        signup.setUsername(requestDto.getUsername());
        signup.setEmail(requestDto.getEmail());
        signup.setOne_liner(requestDto.getOne_liner());

        return signUpRepository.save(signup);
    }

    // 유저 로그인 메서드
    public boolean loginUser(SignUpRequestDto requestDto) {
        Signup signup = signUpRepository.findByUser_id(requestDto.getUser_id());

        if (!signup.getUser_id().equals(requestDto.getUser_id())) {
            throw new IllegalArgumentException("존재하지 않는 아이디 입니다");
        }
        if (!signup.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        return true;
    }
}
