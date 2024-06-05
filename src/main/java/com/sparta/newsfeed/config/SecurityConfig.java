//package com.sparta.newsfeed.config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/user/profile/**").authenticated() // /user/profile/** 경로에 대한 요청은 인증이 필요합니다.
//                .anyRequest().permitAll() // 나머지 요청은 모두 허용합니다.
//                .and()
//                .formLogin() // 기본 로그인 폼 사용
//                .and()
//                .httpBasic(); // HTTP 기본 인증을 사용합니다.
//    }
//}
