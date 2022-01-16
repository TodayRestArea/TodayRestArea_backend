package com.todayrestarea.user.service;

import com.todayrestarea.user.service.dto.LoginRequest;
import com.todayrestarea.user.service.dto.LoginResponse;

public interface UserService {
    LoginResponse handleAuth(LoginRequest requestDto);
}
