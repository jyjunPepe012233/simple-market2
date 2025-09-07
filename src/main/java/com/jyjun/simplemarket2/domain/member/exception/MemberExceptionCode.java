package com.jyjun.simplemarket2.domain.member.exception;

import com.jyjun.simplemarket2.core.exception.CustomExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum MemberExceptionCode implements CustomExceptionCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "없는 멤버"),
    MEMBER_ID_CONFLICTED(HttpStatus.CONFLICT, "중복된 ID")
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
