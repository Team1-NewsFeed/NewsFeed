package com.sparta.newsfeed.dto.CommentDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.newsfeed.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;

    private Long user_id;

    private Long board_user_id;

    private String contents;

    private Long like_count;

    private String massage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime create_at;
    private LocalDateTime modified_at;


    public CommentResponseDto(Comment comment , long like_count ) {
    this.id = comment.getId();
    this.user_id = comment.getUser().getId();
    this.board_user_id = comment.getBoard().getUser().getId();
    this.contents = comment.getContents();
    this.create_at = comment.getCreatedTime();
    this.modified_at = comment.getModifiedTime();
    this.like_count = like_count;
    }

    public CommentResponseDto(Comment comment , long like_count, String massage) {
        this.id = comment.getId();
        this.user_id = comment.getUser().getId();
        this.board_user_id = comment.getBoard().getUser().getId();
        this.contents = comment.getContents();
        this.create_at = comment.getCreatedTime();
        this.modified_at = comment.getModifiedTime();
        this.like_count = like_count;
        this.massage = massage;
    }


}
