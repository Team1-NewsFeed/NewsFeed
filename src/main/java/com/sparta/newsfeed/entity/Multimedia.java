package com.sparta.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Multimedia extends Timer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob// 10 MB 저장 가능함
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    @Lob // 200 MB 저장 가능함
    @Column(name = "movie", columnDefinition = "LONGBLOB")
    private byte[] movie;

    @OneToOne
    @JoinColumn(name = "board_id" )
    private Board board;



}
