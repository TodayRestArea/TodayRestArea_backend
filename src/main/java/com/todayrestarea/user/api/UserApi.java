package com.todayrestarea.user.api;

import com.todayrestarea.auth.jwt.JwtAuthTokenProvider;
import com.todayrestarea.common.dto.BaseException;
import com.todayrestarea.common.dto.ComResponseDto;
import com.todayrestarea.user.service.UserService;
import com.todayrestarea.user.service.dto.LoginRequest;
import com.todayrestarea.user.service.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;
    private JwtAuthTokenProvider jwtAuthTokenProvider;

    @PostMapping("/oauth/kakao")
    public ComResponseDto<LoginResponse> kakaoLogin(@RequestBody LoginRequest request) {
        return ComResponseDto.success(userService.handleAuth(request));
    }

    @DeleteMapping("/user")
    public  ComResponseDto<Object> deleteUser(@RequestHeader("Authorization") String jwtToken) {
        try {
            Long userId = jwtAuthTokenProvider.getPayload(jwtToken).getUserId();
            userService.deleteUser(userId);
            return ComResponseDto.success(null);
        } catch (BaseException exception)
        {
            return ComResponseDto.error(exception.getErrorCode());
        }
    }
}
