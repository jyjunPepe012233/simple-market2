package com.jyjun.simplemarket2.core.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomException extends RuntimeException {

    private final CustomExceptionCode exceptionCode;

}
