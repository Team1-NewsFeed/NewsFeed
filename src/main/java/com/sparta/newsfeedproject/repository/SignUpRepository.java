package com.sparta.newsfeedproject.repository;

import com.sparta.newsfeedproject.entity.SignUp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignUpRepository extends JpaRepository<SignUp, Long> {
}
