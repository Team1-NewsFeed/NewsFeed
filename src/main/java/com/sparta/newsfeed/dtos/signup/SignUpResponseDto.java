package com.sparta.newsfeed.dtos.signup;

public class SignUpResponseDto {

    private String username;
    private String userId;
    private String msg;

    public SignUpResponseDto(String username, String userId) {
        this.username = username;
        this.userId = userId;
    }

    public SignUpResponseDto(String msg) {
        this.msg = msg;
    }
}
