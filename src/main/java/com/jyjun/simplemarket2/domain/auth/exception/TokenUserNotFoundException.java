package com.jyjun.simplemarket2.domain.auth.exception;

import com.jyjun.simplemarket2.core.exception.CustomException;

public class TokenUserNotFoundException extends CustomException {
    public TokenUserNotFoundException() {
        super(AuthExceptionCode.TOKEN_USER_NOT_FOUND);
    }
}
