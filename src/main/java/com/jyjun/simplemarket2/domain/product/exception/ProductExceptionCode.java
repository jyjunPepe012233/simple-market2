package com.jyjun.simplemarket2.domain.product.exception;

import com.jyjun.simplemarket2.core.exception.CustomExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ProductExceptionCode implements CustomExceptionCode {

    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "없는 상품")
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
