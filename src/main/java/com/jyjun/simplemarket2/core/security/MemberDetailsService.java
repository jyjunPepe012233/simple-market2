package com.jyjun.simplemarket2.core.security;

import com.jyjun.simplemarket2.domain.member.entity.Member;
import com.jyjun.simplemarket2.persistence.member.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<Member> memberOptional = memberRepo.findById(username);

            Member member = memberOptional.orElseThrow(() -> {
                return new UsernameNotFoundException("MemberDetailsService에서 사용자를 찾을 수 없습니다: " + username);
            });

            return new MemberDetails(member);

        } catch (Exception e) {
            throw new UsernameNotFoundException("MemberDetailsService에서 사용자를 찾을 수 없습니다: " + username);
        }
    }
}

