package com.jyjun.simplemarket2.application.auth.dto;

public record ReissueRes(
        String refreshToken,
        String accessToken
) {
    public static ReissueRes of(String refreshToken, String accessToken) {
        return new ReissueRes(refreshToken, accessToken);
    }
}
