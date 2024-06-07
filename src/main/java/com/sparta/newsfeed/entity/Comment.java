package com.sparta.newsfeed.entity;

import com.sparta.newsfeed.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id;
    private Long board_user_id;
    private String contents;

    @ManyToOne
    @JoinColumn(name = "board_id" ,insertable = false, updatable = false)
    private Board board;

    public Comment(CommentRequestDto commentRequestDto ,Board board) {
        this.id = commentRequestDto.getId();
        this.user_id = commentRequestDto.getUser_id();
        this.board_user_id = board.getUser_id();
        this.contents = commentRequestDto.getContents();
    }

    public void update(CommentRequestDto commentRequestDto){
        this.contents = commentRequestDto.getContents();

    }
}
