package com.jyjun.simplemarket2.global.exception;

import com.jyjun.simplemarket2.core.exception.CustomExceptionCode;

public record ErrorResponse(
        String exceptionName,
        String message
) {
    public static ErrorResponse of(CustomExceptionCode exceptionCode) {
        return new ErrorResponse(exceptionCode.toString(), exceptionCode.getMessage());
    }
}
