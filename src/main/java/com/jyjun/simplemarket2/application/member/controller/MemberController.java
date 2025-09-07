package com.jyjun.simplemarket2.application.member.controller;

import com.jyjun.simplemarket2.application.member.dto.JoinReq;
import com.jyjun.simplemarket2.domain.member.usecase.MemberUsecase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberUsecase memberUsecase;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid JoinReq req) {
        memberUsecase.signup(req);
        return ResponseEntity.ok("회원 가입 완료");
    }
}
