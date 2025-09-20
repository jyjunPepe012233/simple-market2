package com.jyjun.simplemarket2.core.support;

import com.jyjun.simplemarket2.domain.member.enumeration.MemberRole;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JWTUtil {

    public static final String REFRESH_TOKEN_TYPE = "refresh";
    public static final String ACCESS_TOKEN_TYPE = "access";

    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    /**
     * 생성자에서 application.properties에 저장된 SecretKey 값을 가져와 설정
     */
    public JWTUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration.access}") long accessTokenExpiration,
            @Value("${jwt.expiration.refresh}") long refreshTokenExpiration
    ) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String getJwtFromHttpRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        return authorizationHeader.substring(7);
    }

    public String getJti(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .get("jti", String.class);
    }

    /**
     * JWT에서 username 추출
     */
    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .get("username", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .get("role", String.class);
    }

    public String getTokenType(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .get("type", String.class);
    }

    /**
     * JWT 만료 여부 확인
     */
    public Boolean isTokenExpired(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public String createAccessToken(String username, String role) {
        return createToken(username, role, ACCESS_TOKEN_TYPE, accessTokenExpiration);
    }

    public String createRefreshToken(String username, String role) {
        return createToken(username, role, REFRESH_TOKEN_TYPE, refreshTokenExpiration);
    }

    public String createToken(String username, String role, String tokenType, Long expiredMs) {
        log.info(new Date(System.currentTimeMillis()).toString());
        log.info(new Date(System.currentTimeMillis() + expiredMs).toString());
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .claim("type", tokenType)
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) // 발급 시간
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) // 만료 시간
                .signWith(secretKey) // 비밀키를 사용하여 서명
                .compact();
    }
}