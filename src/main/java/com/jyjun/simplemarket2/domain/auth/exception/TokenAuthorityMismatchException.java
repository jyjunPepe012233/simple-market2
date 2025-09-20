package com.jyjun.simplemarket2.domain.auth.exception;

import com.jyjun.simplemarket2.core.exception.CustomException;

public class TokenAuthorityMismatchException extends CustomException {
    public TokenAuthorityMismatchException() {
        super(AuthExceptionCode.TOKEN_AUTHORITY_MISMATCH);
    }
}
