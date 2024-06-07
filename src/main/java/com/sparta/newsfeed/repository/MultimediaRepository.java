package com.sparta.newsfeed.repository;

import com.sparta.newsfeed.entity.Multimedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultimediaRepository extends JpaRepository<Multimedia, Long> {
}
