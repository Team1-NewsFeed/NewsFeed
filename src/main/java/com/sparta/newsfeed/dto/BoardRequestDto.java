package com.sparta.newsfeed.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {

    private Long id;
    private Long user_id;
    private String contents;

}