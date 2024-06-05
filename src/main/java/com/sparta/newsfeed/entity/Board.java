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
    private long id;

    private String user_id;

    private String contents;

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
