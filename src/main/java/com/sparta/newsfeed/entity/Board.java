package com.sparta.newsfeed.entity;


import com.sparta.newsfeed.dto.boardDto.BoardRequestDto;
import com.sparta.newsfeed.entity.User_entity.User;
import jakarta.persistence.*;
import lombok.Getter;


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

    // 유저
    @ManyToOne
    @JoinColumn(name = "user_id" ,insertable = false, updatable = false)
    private User user;

    private Long likecounts;

    //댓글
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "board_id")
    private List<Comment> commentList;

    // 사진 및 비디오
    @OneToOne(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private Multimedia multimedia;

    public Board() {
    }

    public void setLikecounts(Long likecounts) {
        this.likecounts = likecounts;
    }


    public Board(User user, BoardRequestDto boardRequestDto) {
        this.user_id = user.getId();
        this.contents = boardRequestDto.getContents();
    }

    public Board(User user, BoardRequestDto boardRequestDto, Long likecounts) {
        this.user_id = user.getId();
        this.contents = boardRequestDto.getContents();
        this.likecounts = likecounts;
    }

    public void update( BoardRequestDto boardRequestDto) {
        this.contents = boardRequestDto.getContents();
    }
}
