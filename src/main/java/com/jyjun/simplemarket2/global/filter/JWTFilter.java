package com.jyjun.simplemarket2.global.filter;

import com.jyjun.simplemarket2.core.security.MemberDetails;
import com.jyjun.simplemarket2.core.support.JWTUtil;
import com.jyjun.simplemarket2.domain.member.entity.Member;
import com.jyjun.simplemarket2.domain.member.enumeration.MemberRole;
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
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Authorization 헤더에서 JWT 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            log.info("JWTFilter 34: ");
            return;
        }

        // "Bearer " 이후의 토큰 값만 추출
        String token = authorizationHeader.substring(7);

        log.info("JWTFilter 41: " + token);

        try {
            // JWT 만료 여부 검증
            if (jwtUtil.isTokenExpired(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰이 만료되었습니다.");
                return;
            }

            log.info("JWTFilter 50: ");

            log.info("JWTFilter 52: " + jwtUtil.getRole(token));

            // JWT에서 사용자 정보 추출
            String userId = jwtUtil.getUsername(token);
            MemberRole role = MemberRole.valueOf(jwtUtil.getRole(token));

            log.info("JWTFilter 56: " + role);

            log.info(role.name());

            // 인증 객체 생성
            Member member = Member.builder()
                    .id(userId)
                    .password("N/A") // 비밀번호는 JWT 기반 인증이므로 사용하지 않음
                    .role(role)
                    .build();

            log.info("JWTFilter 67: " + member.getRole());

            MemberDetails memberDetails = new MemberDetails(member);
            Authentication authToken = new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());

            // SecurityContext에 인증 정보 저장 (STATLESS 모드이므로 요청 종료 시 소멸)
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        } catch (Exception e) {
            log.error("JWT 필터 처리 중 오류 발생: {}", e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
        }

        filterChain.doFilter(request, response);
    }
}