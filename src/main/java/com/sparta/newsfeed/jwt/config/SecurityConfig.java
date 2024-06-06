package com.sparta.newsfeed.jwt.config;

import com.sparta.newsfeed.jwt.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화
                .authorizeHttpRequests(auth -> auth
                        // 예외처리할 경로들. TODO : 이중에서 로그아웃과 회원탈퇴 경로는 빼야하지만 현재 인증 인가 어느쪽인지 모르곘는데 에러가 발생함.
                        .requestMatchers("/user/signup","/user/login", "/user/logout", "/user/delete").permitAll() // 회원가입 경로는 인증 없이 접근 허용
                        // 그외 요청을 모두 인증이 필요하게 설정.
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class); // JWT 필터 등록

        return http.build();
    }
}
