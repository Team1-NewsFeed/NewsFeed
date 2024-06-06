package com.sparta.newsfeed.dtos.signup;

import com.sparta.newsfeed.entity.User;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    private String userId;
    private String password;
    private String username;
    private String email;
    private String one_liner;
}
