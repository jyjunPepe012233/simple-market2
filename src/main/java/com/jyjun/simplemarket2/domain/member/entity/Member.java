package com.jyjun.simplemarket2.domain.member.entity;

import com.jyjun.simplemarket2.application.member.dto.JoinReq;
import com.jyjun.simplemarket2.core.entity.BaseEntity;
import com.jyjun.simplemarket2.domain.member.enumeration.MemberRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Entity // 테이블 생성
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 빌더 사용 시 기본 생성자는 필수. 정확한 이유는 모름. 내부 로직 때문인 듯함.
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id // @Entity 어노테이션이 있으면 필수인 속성
    private String id;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @NotNull
    private String email;
}
