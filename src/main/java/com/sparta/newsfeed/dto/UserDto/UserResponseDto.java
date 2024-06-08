package com.sparta.newsfeed.dto.UserDto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private Long board_id;
    private Long board_user_id;
    private String board_contents;
    private LocalDateTime created_time;
    private LocalDateTime modifie_time;


}
