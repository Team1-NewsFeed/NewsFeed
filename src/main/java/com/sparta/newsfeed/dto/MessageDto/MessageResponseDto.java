package com.sparta.newsfeed.dto.MessageDto;

import lombok.Getter;

@Getter
public class MessageResponseDto {
    private String message;
    // 회원가입시에 토큰을 반환해 줄 수 있도록 토큰 필드 선언

    public MessageResponseDto(String message) {
        this.message = message;
    }
}
