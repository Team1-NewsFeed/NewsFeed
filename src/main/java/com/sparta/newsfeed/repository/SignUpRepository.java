package com.sparta.newsfeed.repository;

import com.sparta.newsfeed.entity.SignUp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignUpRepository extends JpaRepository<SignUp, Long> {
}
