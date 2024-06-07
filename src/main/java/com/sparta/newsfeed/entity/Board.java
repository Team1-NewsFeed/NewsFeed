package com.sparta.newsfeed.entity;


import com.sparta.newsfeed.dto.BoardRequestDto;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Board extends Timer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유저 아이디
    private Long user_id;

    //내용
    private String contents;

    //댓글
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "board_id")
    private List<Comment> commentList;

    // 사진 및 비디오
    @OneToOne(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private Multimedia multimedia;

    public Board() {
    }

    public Board(HttpServletRequest servletRequest, BoardRequestDto boardRequestDto) {
        this.user_id = boardRequestDto.getUser_id();
        this.contents = boardRequestDto.getContents();
    }

    public void update( BoardRequestDto boardRequestDto) {
        this.contents = boardRequestDto.getContents();
    }
}
