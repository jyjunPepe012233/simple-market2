package com.jyjun.simplemarket2.domain.auth.service;

import com.jyjun.simplemarket2.application.auth.dto.ReissueReq;
import com.jyjun.simplemarket2.application.auth.dto.ReissueRes;
import com.jyjun.simplemarket2.core.support.JWTUtil;
import com.jyjun.simplemarket2.core.support.RedisUtil;
import com.jyjun.simplemarket2.domain.auth.exception.TokenAuthorityMismatchException;
import com.jyjun.simplemarket2.domain.auth.exception.TokenExpiredException;
import com.jyjun.simplemarket2.domain.auth.exception.BadTokenException;
import com.jyjun.simplemarket2.domain.auth.exception.TokenUserNotFoundException;
import com.jyjun.simplemarket2.domain.auth.usecase.AuthUseCase;
import com.jyjun.simplemarket2.domain.member.entity.Member;
import com.jyjun.simplemarket2.domain.member.enumeration.MemberRole;
import com.jyjun.simplemarket2.domain.member.exception.MemberNotFoundException;
import com.jyjun.simplemarket2.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    public ReissueRes reissue(ReissueReq req) {
        String username = jwtUtil.getUsername(req.refreshToken());
        String userRole = jwtUtil.getRole(req.refreshToken());
        verifyUserInfoToReissue(username, userRole);
        verifyTokenToReissue(req.refreshToken(), username);

        String refreshToken = jwtUtil.createRefreshToken(username, userRole);
        String accessToken = jwtUtil.createAccessToken(username, userRole);
        redisUtil.saveRefreshToken(username, refreshToken);
        return ReissueRes.of(refreshToken, accessToken);
    }

    private void verifyUserInfoToReissue(String usernameInToken, String userRoleInToken) {
        Member member = null;
        try {
            member = memberService.getMemberById(usernameInToken);
        } catch (MemberNotFoundException e) {
            throw new TokenUserNotFoundException();
        }

        if (member.getRole() != MemberRole.valueOf(userRoleInToken))
            throw new TokenAuthorityMismatchException();
    }

    private void verifyTokenToReissue(String token, String username) {
        if (!jwtUtil.getTokenType(token).equals(JWTUtil.REFRESH_TOKEN_TYPE))
            throw new BadTokenException();

        if (jwtUtil.isTokenExpired(token))
            throw new TokenExpiredException();

        String tokenId = jwtUtil.getJti(token);
        if (!tokenId.equals(redisUtil.getRefreshTokenJti(username))) {
            deleteRefreshTokenFamily(username);
            throw new TokenExpiredException();
        }
    }

    private void deleteRefreshTokenFamily(String username) {
        redisUtil.deleteRefreshTokenJti(username);
    }
}
