package com.jyjun.simplemarket2.domain.auth.exception;

import com.jyjun.simplemarket2.core.exception.CustomException;

public class BadTokenException extends CustomException {
    public BadTokenException() {
        super(AuthExceptionCode.BAD_TOKEN);
    }
}
