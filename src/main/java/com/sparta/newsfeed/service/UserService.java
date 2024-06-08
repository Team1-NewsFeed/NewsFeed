package com.sparta.newsfeed.service;

import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.dto.UserRequestDto;
import com.sparta.newsfeed.dto.UserResponseDto;
import com.sparta.newsfeed.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserResponseDto getUserProfile(Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));

        UserResponseDto response = new UserResponseDto();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setOne_liner(user.getOne_liner());

        return response;
    }

    public UserResponseDto updateUserProfile(Long userId, UserRequestDto userRequest) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));

        if (userRequest.getUsername() != null) user.setUsername(userRequest.getUsername());
        if (userRequest.getEmail() != null) user.setEmail(userRequest.getEmail());
        if (userRequest.getOne_liner() != null) user.setOne_liner(userRequest.getOne_liner());

        // 비밀번호 변경 여부 확인 및 처리
        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            // 현재 비밀번호 확인
            if (!passwordEncoder.matches(userRequest.getCurrentPassword(), user.getPassword())) {
                throw new Exception("현재 비밀번호가 올바르지 않습니다.");
            }
            // 새 비밀번호가 현재 비밀번호와 같은지 확인
            if (passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
                throw new Exception("새 비밀번호는 현재 비밀번호와 동일할 수 없습니다.");
            }
            // 새 비밀번호를 설정
            String newPassword = userRequest.getPassword();
            // 비밀번호 형식이 올바르지 않은 경우 예외 처리
            if (!isValidPasswordFormat(newPassword)) {
                throw new Exception("올바르지 않은 비밀번호 형식입니다");
            }
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        userRepository.save(user);

        UserResponseDto response = new UserResponseDto();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setOne_liner(user.getOne_liner());

        return response;
    }

    // 비밀번호 형식이 올바른지 확인하는 메서드
    private boolean isValidPasswordFormat(String password) {
        // 비밀번호가 8자 이상, 숫자와 영문자를 혼합하여 구성되어 있는지 확인
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        return password.matches(regex);
    }
}
