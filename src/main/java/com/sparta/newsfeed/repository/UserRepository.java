package com.sparta.newsfeed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sparta.newsfeed.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
//    User findByUsername(String username);
}
