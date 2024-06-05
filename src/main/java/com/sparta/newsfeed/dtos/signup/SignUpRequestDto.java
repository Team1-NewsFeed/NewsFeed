package com.sparta.newsfeed.dtos.signup;

import lombok.Getter;

@Getter
public class SignUpRequestDto {

    private String user_id;
    private String password;
    private String username;
    private String email;
    private String one_liner;
}
