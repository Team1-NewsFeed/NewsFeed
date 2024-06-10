package com.sparta.newsfeed.repository;

import com.sparta.newsfeed.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByCreatedTimeBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
