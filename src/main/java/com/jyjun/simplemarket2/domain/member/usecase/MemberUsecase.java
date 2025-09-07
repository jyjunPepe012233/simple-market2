package com.jyjun.simplemarket2.domain.member.usecase;

import com.jyjun.simplemarket2.application.member.dto.JoinReq;
import com.jyjun.simplemarket2.domain.member.entity.Member;

import java.util.List;

public interface MemberUsecase {

    void signup(JoinReq req);

    List<Member> getMemberAll();

    Member getMemberById(String id);

}
