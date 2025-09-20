package com.jyjun.simplemarket2.application.auth.controller;

import com.jyjun.simplemarket2.application.auth.dto.ReissueReq;
import com.jyjun.simplemarket2.application.auth.dto.ReissueRes;
import com.jyjun.simplemarket2.domain.auth.usecase.AuthUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @GetMapping("/reissue")
    public ResponseEntity<ReissueRes> reissue(@RequestBody @Valid ReissueReq req) {
        return ResponseEntity.ok(authUseCase.reissue(req));
    }

}
