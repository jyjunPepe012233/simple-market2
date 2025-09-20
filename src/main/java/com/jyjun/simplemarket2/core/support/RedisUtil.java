package com.jyjun.simplemarket2.core.support;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;
    private final JWTUtil jwtUtil;

    public void saveRefreshToken(String loginId, String refreshTokenId) {
        redisTemplate.opsForValue().set(loginId, jwtUtil.getJti(refreshTokenId));
    }

    public String getRefreshTokenJti(String loginId) {
        return redisTemplate.opsForValue().get(loginId);
    }

    public void deleteRefreshTokenJti(String loginId) {
        redisTemplate.delete(loginId);
    }

}
