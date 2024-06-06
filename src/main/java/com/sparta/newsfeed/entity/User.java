package com.sparta.newsfeed.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userId;

    @Column
    @NotBlank(message = "유저이름은 비워둘 수 없습니다.")
    private String username;

    @Column
    @NotBlank(message = "비밀번호는 비워둘 수 없습니다.")
    private String password;

    @Column
    @NotBlank(message = "이메일은 비워둘 수 없습니다.")
    private String email;

    @Column
    @NotBlank(message = "한줄 소개는 비워둘 수 없습니다.")
    private String one_liner;

    @Column
    private String user_status;

    @Column
    private String refresh_token;

    public void setUser_id(String userId) {
        this.userId = userId;
    }

    public void setUsername(@NotBlank(message = "유저이름은 비워둘 수 없습니다.") String username) {
        this.username = username;
    }

    public void setPassword(@NotBlank(message = "비밀번호는 비워둘 수 없습니다.") String password) {
        this.password = password;
    }

    public void setEmail(@NotBlank(message = "이메일은 비워둘 수 없습니다.") String email) {
        this.email = email;
    }

    public void setOne_liner(@NotBlank(message = "한줄 소개는 비워둘 수 없습니다.") String one_liner) {
        this.one_liner = one_liner;
    }
}
