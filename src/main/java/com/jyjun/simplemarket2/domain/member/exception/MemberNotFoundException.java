package com.jyjun.simplemarket2.domain.member.exception;

import com.jyjun.simplemarket2.core.exception.CustomException;

public class MemberNotFoundException extends CustomException {

    public MemberNotFoundException() {
        super(MemberExceptionCode.MEMBER_NOT_FOUND);
    }

}
