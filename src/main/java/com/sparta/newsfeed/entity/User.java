package com.sparta.newsfeed.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;

    private String username;

    private String password;

    private String email;

    private String one_liner;

    // 유저의 상태코드. 기본값은 정상임.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus = UserStatus.ACTIVE;

    @Column
    private String refresh_token;

    public void setUser_id(@NotBlank String userId) {
        this.userId = userId;
    }

    public void setPassword(@NotBlank(message = "비밀번호는 비워둘 수 없습니다.") String password) {
        this.password = password;
    }

    // 나중에 프로필 편집에서 설정할 사용자 이름 Setter
    public void setUsername(@NotBlank(message = "유저이름은 비워둘 수 없습니다.") String username) {
        this.username = username;
    }

    // 나중에 프로필 편집에서 사용할 이메일 Setter
    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    // 마찬가지로 한마디 Setter
    public void setOne_liner(@NotNull String one_liner) {
        this.one_liner = one_liner;
    }

    // 기본값 정상
    public void setUserStatus(@NotBlank UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
