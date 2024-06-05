package com.sparta.newsfeed.jwt.dto;
import lombok.Getter;

@Getter
public class AuthRequest {
    private String username;
    private String password;
}
