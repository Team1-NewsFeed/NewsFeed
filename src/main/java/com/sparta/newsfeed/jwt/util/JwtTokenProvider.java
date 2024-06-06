package com.sparta.newsfeed.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import com.sparta.newsfeed.jwt.config.JwtConfig;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class JwtTokenProvider {

    private static SecretKey SECRET_KEY;
    private static final long ACCESS_EXPIRATION = 1800000L; // 30 분간 지속되는 엑세스 토큰
    private static final long REFRESH_EXPIRATION = 1209600000L; // 2 주간 지속되는 리프레쉬 토큰

    static {
        SECRET_KEY = Keys.hmacShaKeyFor(JwtConfig.staticSecretKey.getBytes());
    }

    private JwtTokenProvider() {
    }

    public static String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userId, ACCESS_EXPIRATION);
    }

    public static String generateRefreshToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userId, REFRESH_EXPIRATION);
    }

    private static String createToken(Map<String, Object> claims, String subject, long expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static Boolean validateToken(String token) {
        try {
            String userId = extractUsername(token);
            System.out.println("유효성 검사할 토큰의 사용자 ID: " + userId);
            return !isTokenExpired(token);
        } catch (JwtException e) {
            System.out.println("토큰 유효성 검사 실패: " + e.getMessage());
            return false;
        }
    }

    private static Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // 토큰 만료 확인 메서드
    public static boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }
}
