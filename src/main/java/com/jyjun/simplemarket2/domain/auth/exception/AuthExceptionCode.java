package com.jyjun.simplemarket2.domain.auth.exception;

import com.jyjun.simplemarket2.core.exception.CustomExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AuthExceptionCode implements CustomExceptionCode {

    BAD_TOKEN(HttpStatus.BAD_REQUEST, "잘못된 토큰"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰"),
    TOKEN_USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "없는 사용자"),
    TOKEN_AUTHORITY_MISMATCH(HttpStatus.FORBIDDEN, "권한 불일치"),
    ;

    private final HttpStatus status;
    private final String message;

    @Override
    public int getStatus() {
        return status.value();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
