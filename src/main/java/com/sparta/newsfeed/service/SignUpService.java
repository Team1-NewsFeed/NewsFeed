package com.sparta.newsfeed.service;



import com.sparta.newsfeed.dto.UserDto.LoginRequestDto;
import com.sparta.newsfeed.dto.UserDto.SignUpRequestDto;
import com.sparta.newsfeed.dto.UserDto.UserRequestDto;
import com.sparta.newsfeed.entity.User_entity.User;
import com.sparta.newsfeed.entity.User_entity.UserStatus;
import com.sparta.newsfeed.jwt.util.JwtTokenProvider;
import com.sparta.newsfeed.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Getter
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final JwtTokenProvider jwtTokenProvider;
    private SignUpRequestDto requestDto;

    // 유저 회원가입
    public User addUser(SignUpRequestDto requestDto) {
        this.requestDto = requestDto;
        User existingUser = userRepository.findByUserId(requestDto.getUserId());
        if (existingUser != null) {
            if (existingUser.getUserStatus() == UserStatus.ACTIVE) {
                throw new IllegalArgumentException("중복된 사용자 ID입니다.");
            } else if (existingUser.getUserStatus() == UserStatus.WITHDRAWAL) {
                throw new IllegalArgumentException("탈퇴한 사용자 ID입니다.");
            }
        }

        User user = new User(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        return userRepository.save(user);
    }

    // 유저 로그인
    public Map<String, String> loginUser(SignUpRequestDto requestDto , HttpServletResponse response) {
        User user = userRepository.findByUserId(requestDto.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("유저 아이디가 올바르지 않습니다.");
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("유저 비밀번호가 올바르지 않습니다.");
        }

        if (user.getUserStatus() == UserStatus.WITHDRAWAL) {
            throw new IllegalArgumentException("이미 탈퇴한 사용자 입니다.");
        }

        // 로그인 시 액세스 토큰 및 리프레시 토큰 생성 및 저장
        String accessToken = jwtTokenProvider.generateToken(user.getUserId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUserId());
        jwtTokenProvider.addToken(accessToken,  response);
        //토큰 자동 저장

        user.setRefresh_token(refreshToken);
        userRepository.save(user);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    // 로그아웃 메서드
    // 로그아웃시 리프레쉬 토큰 없애기.
    public void logoutUser(String token) {
        try {
/*            User user = userRepository.findByUserId(userId);
            user.setRefresh_token(null); // 로그아웃시 리프레쉬 토큰 null 하기.
            userRepository.save(user); */
        } catch (Exception e) {
            System.out.println("로그아웃 과정에서 예외 발생: " + e.getMessage());
            throw e;
        }
    }

    // 회원 탈퇴 메서드
    public void deleteUser(String userId, String password) {
        System.out.println("회원 탈퇴 요청을 받았습니다: " + userId);
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new IllegalArgumentException("유저 아이디가 올바르지 않습니다.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("유저 비밀번호가 올바르지 않습니다.");
        }

        if (user.getUserStatus() == UserStatus.WITHDRAWAL) {
            throw new IllegalArgumentException("이미 탈퇴한 사용자입니다.");
        }

        user.setUserStatus(UserStatus.WITHDRAWAL);
        userRepository.save(user);
        System.out.println("사용자 " + userId + "가 성공적으로 탈퇴되었습니다.");
    }


}