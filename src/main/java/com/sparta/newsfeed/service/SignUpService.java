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
}
