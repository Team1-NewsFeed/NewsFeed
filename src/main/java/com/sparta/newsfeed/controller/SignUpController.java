package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dtos.message.MessageResponseDto;
import com.sparta.newsfeed.dtos.signup.SignUpRequestDto;
import com.sparta.newsfeed.dtos.signup.SignUpResponseDto;
import com.sparta.newsfeed.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignUpController {
    @Autowired
    SignUpService signUpService;

    // 회원가입 api
    @PostMapping("/user/signup")
    public ResponseEntity<MessageResponseDto> addUser(@RequestBody SignUpRequestDto requestDto) {
        String token = signUpService.addUser(requestDto);
        MessageResponseDto messageResponseDto = new MessageResponseDto("회원가입 성공", token);
        return new ResponseEntity<>(messageResponseDto, HttpStatus.CREATED);
    }
}
