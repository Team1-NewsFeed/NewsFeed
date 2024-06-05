package com.sparta.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class SignUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sign_up_id;

    @Column(nullable = false)
    private Long user_id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String one_liner;

    @Column(nullable = false)
    private String user_status;

    @Column(nullable = false)
    private String refresh_token;

}
