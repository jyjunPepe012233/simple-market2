package com.jyjun.simplemarket2.domain.auth.service;

import com.jyjun.simplemarket2.application.auth.dto.ReissueReq;
import com.jyjun.simplemarket2.core.support.JWTUtil;
import com.jyjun.simplemarket2.domain.auth.exception.TokenExpiredException;
import com.jyjun.simplemarket2.domain.auth.exception.TokenInvalidException;
import com.jyjun.simplemarket2.domain.auth.usecase.AuthUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final JWTUtil jwtUtil;

    @Override
    public String reissueAccessToken(ReissueReq req) {
        try {
            if (jwtUtil.isTokenExpired(req.refreshToken())) {
                throw new TokenExpiredException();
            }

            String username = jwtUtil.getUsername(req.refreshToken());
            String role = jwtUtil.getRole(req.refreshToken());

            return jwtUtil.createAccessToken(username, role);

        } catch (Exception e) {
            log.error("reissueAccessToken 중 에러 발생: " + e.getMessage());
            throw new TokenInvalidException();
        }
    }
}
