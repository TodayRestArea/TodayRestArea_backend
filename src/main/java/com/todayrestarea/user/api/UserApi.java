package com.todayrestarea.user.api;

import com.todayrestarea.aspect.UserId;
import com.todayrestarea.auth.jwt.JwtAuthTokenProvider;
import com.todayrestarea.auth.jwt.dto.AuthTokenPayload;
import com.todayrestarea.common.dto.BaseException;
import com.todayrestarea.common.dto.ComResponseDto;
import com.todayrestarea.user.service.UserService;
import com.todayrestarea.user.service.dto.LoginRequest;
import com.todayrestarea.user.service.dto.LoginResponse;
import com.todayrestarea.user.service.dto.RefreshTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    @PostMapping("/oauth/kakao")
    public ComResponseDto<LoginResponse> kakaoLogin(@RequestBody LoginRequest request) {
        return ComResponseDto.success(userService.handleAuth(request));
    }

    @PostMapping("/testlogin")
    public ComResponseDto<LoginResponse> testLogin(@RequestBody LoginRequest request) {
        String accessToken = jwtAuthTokenProvider.createAccessToken(AuthTokenPayload.from(1L));
        String refreshToken = jwtAuthTokenProvider.createRefreshToken();

        return ComResponseDto.success(LoginResponse.of(accessToken, refreshToken));
    }

    @DeleteMapping("/users")
    public ComResponseDto<Object> deleteUser(@UserId Long userId) {
        try {
            userService.deleteUser(userId);
            return ComResponseDto.success(null);
        } catch (BaseException exception)
        {
            return ComResponseDto.error(exception.getErrorCode());
        }
    }

    @PostMapping("/users/logout")
    public ComResponseDto<Object> logout(@UserId Long userId) {
        try {
            userService.logout(userId);
            return ComResponseDto.success(null);
        } catch (BaseException exception) {
            return ComResponseDto.error(exception.getErrorCode());
        }
    }

    @PostMapping("/users/refresh/token")
    public ComResponseDto<LoginResponse> refreshAccessToken(@RequestBody RefreshTokenRequest request) {
        try {
            String accessToken = userService.refreshAccessToken(request);
            return ComResponseDto.success(LoginResponse.of(accessToken, request.refreshToken));
        } catch (BaseException exception) {
            return ComResponseDto.error(exception.getErrorCode());
        }
    }
}
