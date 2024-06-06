package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dtos.message.MessageResponseDto;
import com.sparta.newsfeed.dtos.signup.SignUpRequestDto;
import com.sparta.newsfeed.jwt.util.JwtTokenProvider;
import com.sparta.newsfeed.service.SignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpService signUpService;

    @PostMapping("/user/signup")
    public ResponseEntity<MessageResponseDto> addUser(@Valid @RequestBody SignUpRequestDto requestDto) {
        signUpService.addUser(requestDto);
        MessageResponseDto messageResponseDto = new MessageResponseDto("회원가입 성공");
        return new ResponseEntity<>(messageResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/user/login")
    public ResponseEntity<MessageResponseDto> loginUser(@Valid @RequestBody SignUpRequestDto requestDto) {
        Map<String, String> tokens = signUpService.loginUser(requestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokens.get("accessToken"));
        headers.set("RefreshToken", "Bearer " + tokens.get("refreshToken"));
        MessageResponseDto messageResponseDto = new MessageResponseDto("로그인 성공");
        return new ResponseEntity<>(messageResponseDto, headers, HttpStatus.OK);
    }


    @PostMapping("/user/logout")
    public ResponseEntity<MessageResponseDto> logoutUser(@RequestHeader("Authorization") String accessToken) {
        signUpService.logoutUser(accessToken.replace("Bearer ", ""));
        MessageResponseDto messageResponseDto = new MessageResponseDto("로그아웃 성공");
        return new ResponseEntity<>(messageResponseDto, HttpStatus.OK);
    }

}
