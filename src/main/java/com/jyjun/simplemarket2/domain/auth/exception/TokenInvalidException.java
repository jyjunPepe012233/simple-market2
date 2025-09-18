package com.jyjun.simplemarket2.domain.auth.exception;

import com.jyjun.simplemarket2.core.exception.CustomException;

public class TokenInvalidException extends CustomException {
    public TokenInvalidException() {
        super(AuthExceptionCode.TOKEN_INVALID);
    }
}
