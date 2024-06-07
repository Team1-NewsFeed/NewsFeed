package com.sparta.newsfeed.entity;

import com.sparta.newsfeed.dtos.signup.SignUpRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Entity
@Getter
public class User extends Timer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //아이디 비번
    @Column(unique = true)
    private String userId;
    private String password;
    //이름
    private String username;
    //이매일
    private String email;
    //한줄 소개
    private String one_liner;
    //리프레쉬 토큰
    private String refresh_token;

    // 유저의 상태코드. 기본값은 정상임.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus = UserStatus.ACTIVE;

    public User(SignUpRequestDto requestDto) {
        super();
    }

    public void User(SignUpRequestDto requestDto){
        this.userId = requestDto.getUserId();
        this.password = requestDto.getPassword();
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
        this.one_liner = requestDto.getOne_liner();
        this.userStatus = requestDto.getUserStatus();
    }

}