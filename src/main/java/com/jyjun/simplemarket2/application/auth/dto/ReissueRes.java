package com.jyjun.simplemarket2.application.auth.dto;

public record ReissueRes(
        String accessToken
) {
    public static ReissueRes of(String accessToken) {
        return new ReissueRes(accessToken);
    }
}
