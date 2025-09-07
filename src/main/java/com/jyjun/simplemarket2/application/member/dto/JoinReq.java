package com.jyjun.simplemarket2.application.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record JoinReq(
        @NotBlank String id,
        @NotBlank String password,
        @NotBlank String name,
        @NotNull @Email String email
) {
}
