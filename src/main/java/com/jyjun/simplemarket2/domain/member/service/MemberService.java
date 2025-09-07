package com.jyjun.simplemarket2.domain.member.service;

import com.jyjun.simplemarket2.application.member.dto.JoinReq;
import com.jyjun.simplemarket2.domain.member.entity.Member;
import com.jyjun.simplemarket2.domain.member.enumeration.MemberRole;
import com.jyjun.simplemarket2.domain.member.exception.MemberIdConflictedException;
import com.jyjun.simplemarket2.domain.member.exception.MemberNotFoundException;
import com.jyjun.simplemarket2.domain.member.usecase.MemberUsecase;
import com.jyjun.simplemarket2.persistence.member.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberUsecase {

    private final MemberRepository memberRepo;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Override
    public void signup(JoinReq req) {
        validateIdConflict(req.id());
        memberRepo.save(instantiateMember(req));
    }

    private void validateIdConflict(String id) {
        if (memberRepo.existsById(id)) {
            throw new MemberIdConflictedException();
        }
    }

    private Member instantiateMember(JoinReq joinReq) {
        return Member.builder()
                .id(joinReq.id())
                .password(bcryptPasswordEncoder.encode(joinReq.password()))
                .name(joinReq.name())
                .role(MemberRole.ROLE_USER)
                .email(joinReq.email())
                .build();
    }

    @Override
    public List<Member> getMemberAll() {
        return memberRepo.findAll();
    }

    @Override
    public Member getMemberById(String id) {
        return memberRepo.findById(id).orElseThrow(MemberNotFoundException::new);
    }

}
