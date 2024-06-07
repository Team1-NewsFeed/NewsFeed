package com.sparta.newsfeed.jwt.filter;

import com.sparta.newsfeed.jwt.util.JwtTokenProvider;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtFilter implements Filter {

    // 필터에서 경로를 예외처리한 부분. TODO : 현재 예외처리를 하지 않으면 로그아웃과 탈퇴기능이 작동하지 않음. 인증과 인가 쪽에서 에러가 발생하는듯
    // private static final String[] EXCLUDED_PATHS = {"/user/signup", "/user/login", "/error"};

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        System.out.println("JWT 필터 작동 시작.");

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String requestPath = httpServletRequest.getRequestURI();
        System.out.println("요청 경로: " + requestPath);

//        for (String path : EXCLUDED_PATHS) {
//            if (path.equals(requestPath)) {
//                System.out.println("필터 제외 경로: " + path);
//                filterChain.doFilter(servletRequest, servletResponse);
//                System.out.println("필터 제외 후 종료 !!");
//                return;
//            }
//        }

        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        System.out.println("Authorization 헤더: " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                if (JwtTokenProvider.validateToken(token)) {
                    System.out.println("토큰 유효함: " + token);
                    filterChain.doFilter(servletRequest, servletResponse);
                    System.out.println("필터 종료 !!");
                } else {
                    System.out.println("유효하지 않은 토큰: " + token);
                    httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "토큰이 유효하지 않습니다.");
                }
            } catch (JwtException e) {
                System.out.println("토큰 유효성 검사 중 예외 발생: " + e.getMessage());
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "토큰이 유효하지 않습니다.");
            }
        } else {
            System.out.println("Authorization 헤더가 없거나 유효하지 않음");
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "존재하지 않거나 유효하지 않은 Authorization 헤더");
        }
    }


    @Override
    public void destroy() {
    }
}
