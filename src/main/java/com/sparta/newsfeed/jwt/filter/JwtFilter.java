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

    // 아직 토큰을 발급받기 전인 부분에 대해서 필터링을 건너뛸 경로
    private static final String[] EXCLUDED_PATHS = {"/user/signup"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        System.out.println("DO FILTER ");

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String requestPath = httpServletRequest.getRequestURI();

        // 필터링에서 제외할 부분 건너뛰기
        for (String path : EXCLUDED_PATHS) {
            if(path.equals(requestPath)){
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                String username = JwtTokenProvider.extractUsername(token);

                if (JwtTokenProvider.validateToken(token, username)) {
                    httpServletRequest.setAttribute("username", username);
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "토큰이 유효하지 않습니다.");
                }
            } catch (JwtException e) {
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "토큰이 유효하지 않습니다.");
            }
        } else {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "존재하지 않거나 유효하지 않은 Authorization 헤더");
        }
    }

    @Override
    public void destroy() {}
}
