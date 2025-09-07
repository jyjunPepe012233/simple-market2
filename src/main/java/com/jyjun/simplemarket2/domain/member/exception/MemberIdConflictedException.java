package com.jyjun.simplemarket2.domain.member.exception;

import com.jyjun.simplemarket2.core.exception.CustomException;

public class MemberIdConflictedException extends CustomException {

    public MemberIdConflictedException() {
        super(MemberExceptionCode.MEMBER_ID_CONFLICTED);
    }

}
