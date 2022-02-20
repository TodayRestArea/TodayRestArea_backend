package com.todayrestarea.auth.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthTokenPayload {
    private Long userId;

    public static AuthTokenPayload of(Long userId) {
        return new AuthTokenPayload(userId);
    }
}
