package com.jyjun.simplemarket2.domain.auth.exception;

import com.jyjun.simplemarket2.core.exception.CustomException;

public class TokenExpiredException extends CustomException {
    public TokenExpiredException() {
        super(AuthExceptionCode.TOKEN_EXPIRED);
    }
}
