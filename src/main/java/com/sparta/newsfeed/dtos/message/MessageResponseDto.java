package com.sparta.newsfeed.dtos.message;

import lombok.Getter;

@Getter
public class MessageResponseDto {
    private String message;
    // 회원가입시에 토큰을 반환해 줄 수 있도록 토큰 필드 선언
    private String token;

    public MessageResponseDto(String message, String token) {
        this.message = message;
        this.token = token;
    }
}
