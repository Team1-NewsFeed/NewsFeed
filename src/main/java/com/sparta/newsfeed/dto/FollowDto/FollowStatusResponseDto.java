package com.sparta.newsfeed.dto.FollowDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FollowStatusResponseDto {
    private String statusMessage;

    public FollowStatusResponseDto(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}