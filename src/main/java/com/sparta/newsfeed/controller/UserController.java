package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.UserRequest;
import com.sparta.newsfeed.dto.UserResponse;
import com.sparta.newsfeed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user/profile")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public UserResponse getUserProfile(@PathVariable Long userId) throws Exception {
        return userService.getUserProfile(userId);
    }

    @PatchMapping("/{userId}")
    public UserResponse updateUserProfile(@PathVariable Long userId,
                                          @RequestBody UserRequest userRequest,
                                          Principal principal) throws Exception {
        return userService.updateUserProfile(userId, userRequest);
    }
}
