package com.todayrestarea.user.service;

import com.todayrestarea.common.dto.BaseException;
import com.todayrestarea.user.entity.User;
import com.todayrestarea.user.repository.UserRepository;
import com.todayrestarea.user.service.dto.LoginRequest;
import com.todayrestarea.user.service.dto.LoginResponse;
import com.todayrestarea.auth.jwt.JwtAuthTokenProvider;
import com.todayrestarea.auth.jwt.dto.AuthTokenPayload;
import com.todayrestarea.auth.kakao.KakaoClient;
import com.todayrestarea.auth.kakao.dto.KakaoUserResponse;
import com.todayrestarea.user.service.dto.RefreshTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.todayrestarea.common.ErrorCode.UNAUTHORIZED_EXCEPTION;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final KakaoClient kakaoClient;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    public LoginResponse handleAuth(LoginRequest request) {
        KakaoUserResponse userInfo = kakaoClient.getUserInfo(request.getAccessToken());

        User user = userRepository.findByOauthId(userInfo.getId())
                .orElse(signUpUser(userInfo));

        String accessToken = jwtAuthTokenProvider.createAccessToken(AuthTokenPayload.from(user.getUserId()));
        String refreshToken = jwtAuthTokenProvider.createRefreshToken();
        user.updateRefreshToken(refreshToken);

        return LoginResponse.of(accessToken, refreshToken);
    }

    private User signUpUser(KakaoUserResponse userInfo) {
        User newUser = User.newKaKaoInstance(userInfo);
        return userRepository.save(newUser);
    }

    public Optional<User> findUserByToken(String jwtToken) throws BaseException {
        Long userId = jwtAuthTokenProvider.getPayload(jwtToken).getUserId();
        return userRepository.findById(userId);
    }

    public Optional<User> findById(Long userId){
        return userRepository.findById(userId);
    }

    public void deleteUser(Long userId) throws BaseException {
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(UNAUTHORIZED_EXCEPTION));
        userRepository.delete(user);
    }

    @Override
    public void logout(Long userId) throws BaseException {
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(UNAUTHORIZED_EXCEPTION));
        user.removeRefreshToken();
    }

    @Override
    public String refreshAccessToken(RefreshTokenRequest request) throws BaseException {
        jwtAuthTokenProvider.validateRefreshToken(request.refreshToken);
        User user = userRepository.findByRefreshToken(request.refreshToken).orElseThrow(() -> new BaseException(UNAUTHORIZED_EXCEPTION));
        return jwtAuthTokenProvider.createAccessToken(AuthTokenPayload.from(user.getUserId()));
    }
}
