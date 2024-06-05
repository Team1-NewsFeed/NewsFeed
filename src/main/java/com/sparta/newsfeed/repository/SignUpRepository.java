package com.sparta.newsfeed.repository;

import com.sparta.newsfeed.entity.Signup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignUpRepository extends JpaRepository<Signup, Long> {
    Signup findByUser_id(String userId);
}
