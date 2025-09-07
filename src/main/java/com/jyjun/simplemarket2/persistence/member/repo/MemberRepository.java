package com.jyjun.simplemarket2.persistence.member.repo;

import com.jyjun.simplemarket2.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {

}
