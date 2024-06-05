package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.repository.SignUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpRepository SignUpRepository;



}
