package com.jyjun.simplemarket2.global.exception;

import com.jyjun.simplemarket2.core.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ResponseEntity
                .status(e.getExceptionCode().getStatus())
                .body(ErrorResponse.of(e.getExceptionCode()));
    }

    // TODO: Validation Error 핸들링 필요

}
