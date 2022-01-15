package com.todayrestarea.user.util.jwt.dto;

import com.todayrestarea.user.service.dto.LoginResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthTokenPayload {
    private Long userSeq;

    public static AuthTokenPayload of(Long userSeq) {
        return new AuthTokenPayload(userSeq);
    }
}
