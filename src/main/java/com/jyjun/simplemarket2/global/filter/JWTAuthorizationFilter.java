package com.jyjun.simplemarket2.global.filter;

import com.jyjun.simplemarket2.core.security.MemberDetails;
import com.jyjun.simplemarket2.core.support.JWTUtil;
import com.jyjun.simplemarket2.core.support.RedisUtil;
import com.jyjun.simplemarket2.domain.member.entity.Member;
import com.jyjun.simplemarket2.domain.member.enumeration.MemberRole;
import io.netty.util.internal.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.getJwtFromHttpRequest(request);

        if (StringUtil.isNullOrEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtUtil.getTokenType(token).equals("access")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 토큰");
            return;
        }

        if (jwtUtil.isTokenExpired(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료된 토큰");
            return;
        }

        String username = jwtUtil.getUsername(token);
        MemberRole userRole = MemberRole.valueOf(jwtUtil.getRole(token));

        Member dummyMember = Member.builder()
                .id(username)
                .password("N/A")
                .role(userRole)
                .build();

        MemberDetails memberDetails = new MemberDetails(dummyMember);
        Authentication authToken = new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}