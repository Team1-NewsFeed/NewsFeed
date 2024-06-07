package com.sparta.newsfeed.service;

import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.dto.UserRequest;
import com.sparta.newsfeed.dto.UserResponse;
import com.sparta.newsfeed.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public UserResponse getUserProfile(Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setOne_liner(user.getOne_liner());

        return response;
    }

    public UserResponse updateUserProfile(Long userId, UserRequest userRequest) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));

//        if (!passwordEncoder.matches(userRequest.getCurrentPassword(), user.getPassword())) {
//            throw new Exception("Current password is incorrect");
//        }
//
//        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
//            if (passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
//                throw new Exception("New password cannot be the same as the current password");
//            }
//            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
//        }

        if (userRequest.getUsername() != null) user.setUsername(userRequest.getUsername());
        if (userRequest.getEmail() != null) user.setEmail(userRequest.getEmail());
        if (userRequest.getOne_liner() != null) user.setOne_liner(userRequest.getOne_liner());

        userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setOne_liner(user.getOne_liner());

        return response;
    }
}