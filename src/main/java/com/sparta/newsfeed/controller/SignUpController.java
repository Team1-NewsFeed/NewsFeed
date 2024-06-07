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

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpService signUpService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<MessageResponseDto> addUser(@Valid @RequestBody SignUpRequestDto requestDto) {
        signUpService.addUser(requestDto);
        MessageResponseDto messageResponseDto = new MessageResponseDto("회원가입 성공");
        return new ResponseEntity<>(messageResponseDto, HttpStatus.CREATED);
    }

    // 로그인
    @PostMapping("/user/login")
    public ResponseEntity<MessageResponseDto> loginUser(@Valid @RequestBody SignUpRequestDto requestDto, HttpServletResponse response) {
        Map<String, String> tokens = signUpService.loginUser(requestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokens.get("accessToken"));
        headers.set("RefreshToken", "Bearer " + tokens.get("refreshToken"));

        // 쿠키 생성 및 설정
        Cookie accessTokenCookie = new Cookie("accessToken", tokens.get("accessToken"));
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(1800); // 30분

        Cookie refreshTokenCookie = new Cookie("refreshToken", tokens.get("refreshToken"));
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(1209600); // 2주

        // 응답에 쿠키 추가
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        MessageResponseDto messageResponseDto = new MessageResponseDto("로그인 성공");
        return new ResponseEntity<>(messageResponseDto, headers, HttpStatus.OK);
    }

    // 로그아웃
    @PostMapping("/user/logout")
    public ResponseEntity<MessageResponseDto> logoutUser(@RequestHeader("Authorization") String accessToken, HttpServletResponse response) {
        signUpService.logoutUser(accessToken.replace("Bearer ", ""));

        // 쿠키 삭제
        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0); // 쿠키 삭제

        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0); // 쿠키 삭제

        // 응답에 쿠키 추가
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        MessageResponseDto messageResponseDto = new MessageResponseDto("로그아웃 성공");
        return new ResponseEntity<>(messageResponseDto, HttpStatus.OK);
    }

    // 회원 탈퇴
    @PostMapping("/user/delete")
    public ResponseEntity<MessageResponseDto> deleteUser(@RequestHeader("Authorization") String accessToken, @Valid @RequestBody SignUpRequestDto requestDto) {
        String userId = jwtTokenProvider.extractUsername(accessToken.replace("Bearer ", ""));
        signUpService.deleteUser(userId, requestDto.getPassword());
        MessageResponseDto messageResponseDto = new MessageResponseDto("회원탈퇴 성공");
        return new ResponseEntity<>(messageResponseDto, HttpStatus.OK);

    }
}
