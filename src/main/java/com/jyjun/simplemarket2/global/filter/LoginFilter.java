package com.jyjun.simplemarket2.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyjun.simplemarket2.core.security.MemberDetails;
import com.jyjun.simplemarket2.core.support.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 로그인 요청 시 사용자 인증 처리
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {

        if (!req.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + req.getMethod());
        }

        if (!req.getContentType().contains("application/json")) {
            throw new AuthenticationServiceException("Authentication content-type not supported: " + req.getContentType());
        }

        try {
            String jsonBody = StreamUtils.copyToString(req.getInputStream(), StandardCharsets.UTF_8);
            LoginRequest loginReq = objectMapper.readValue(jsonBody, LoginRequest.class);

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginReq.username(), loginReq.password());

            log.info("username: " + loginReq.username() + " password: " + loginReq.password());

            return this.authenticationManager.authenticate(authRequest);

        } catch (Exception e) {
            throw new AuthenticationServiceException("Some error occurred while parse json body request: " + e.getMessage());
        }

    }

    /**
     * 로그인 성공 시 JWT 토큰 발급
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
        log.info("46: ");

        MemberDetails customUserDetails = (MemberDetails) auth.getPrincipal();
        String username = customUserDetails.getUsername();

        // 사용자 역할(Role) 조회
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority authority = iterator.next();

        String role = authority.getAuthority();
        String token = jwtUtil.createJwt(username, role, 3 * 60 * 1000L); // 3분 유효 토큰 생성

        // 응답 타입 JSON으로 지정
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        // JSON 형태로 응답 작성
        String responseBody = "{ \"accessToken\": \"" + token + "\" }";
        res.getWriter().write(responseBody);
    }

    /**
     * 로그인 실패 시 401 응답 반환
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) {
        log.info("LoginFilter 64: ");
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized 응답
    }
}

record LoginRequest (
        String username,
        String password
) {
}