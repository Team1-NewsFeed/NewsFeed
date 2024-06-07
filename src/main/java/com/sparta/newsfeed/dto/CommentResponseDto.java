package com.sparta.newsfeed.dto;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime create_at;
    private LocalDateTime modified_at;

    public CommentResponseDto(Long id, String content) {
        this.id = id;
        this.user_id = user_id;
        this.board_user_id = board_user_id;
        this.contents = this.contents;
        this.like_count = like_count;
        this.create_at = create_at;
        this.modified_at = modified_at;
    }

    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
//                comment.getUserId(),
//                comment.getBoardUserId(),
                comment.getContents()
//                comment.getLikeCount(),
//                comment.getCreatedAt(),
//                comment.getModifiedAt()
        );
    }
}
