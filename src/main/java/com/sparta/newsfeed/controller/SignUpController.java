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

    // 회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<MessageResponseDto> addUser(@Valid @RequestBody SignUpRequestDto requestDto) {
        signUpService.addUser(requestDto);
        MessageResponseDto messageResponseDto = new MessageResponseDto("회원가입 성공");
        return new ResponseEntity<>(messageResponseDto, HttpStatus.CREATED);
    }

    // 로그인
    @PostMapping("/user/login")
    public ResponseEntity<MessageResponseDto> loginUser(@Valid @RequestBody SignUpRequestDto requestDto) {
        Map<String, String> tokens = signUpService.loginUser(requestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokens.get("accessToken"));
        headers.set("RefreshToken", "Bearer " + tokens.get("refreshToken"));
        MessageResponseDto messageResponseDto = new MessageResponseDto("로그인 성공");
        return new ResponseEntity<>(messageResponseDto, headers, HttpStatus.OK);
    }

    // 로그아웃
    // TODO : 현재 필터에서 제대로 넘겨주지 못하는 에러가 발생. 디버깅만 3시간 했지만 찾지 못하였음
    @PostMapping("/user/logout")
    public ResponseEntity<MessageResponseDto> logoutUser(@RequestHeader("Authorization") String accessToken) {
        signUpService.logoutUser(accessToken.replace("Bearer ", ""));
        MessageResponseDto messageResponseDto = new MessageResponseDto("로그아웃 성공");
        return new ResponseEntity<>(messageResponseDto, HttpStatus.OK);
    }

    // 회원 탈퇴 api
    @PostMapping("/user/delete")
    public ResponseEntity<MessageResponseDto> deleteUser(@RequestHeader("Authorization") String accessToken, @Valid @RequestBody SignUpRequestDto requestDto) {
        String userId = JwtTokenProvider.extractUsername(accessToken.replace("Bearer ", ""));
        signUpService.deleteUser(userId, requestDto.getPassword());
        MessageResponseDto messageResponseDto = new MessageResponseDto("회원탈퇴 성공");
        return new ResponseEntity<>(messageResponseDto, HttpStatus.OK);

    }

}
