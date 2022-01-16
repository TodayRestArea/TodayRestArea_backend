package com.todayrestarea.user.service;

import com.todayrestarea.user.domain.User;
import com.todayrestarea.user.repository.UserRepository;
import com.todayrestarea.user.service.dto.LoginRequest;
import com.todayrestarea.user.service.dto.LoginResponse;
import com.todayrestarea.user.util.jwt.JwtAuthTokenProvider;
import com.todayrestarea.user.util.jwt.dto.AuthTokenPayload;
import com.todayrestarea.user.util.kakao.KakaoClient;
import com.todayrestarea.user.util.kakao.dto.KakaoUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final KakaoClient kakaoClient;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    public LoginResponse handleAuth(LoginRequest request) {
        KakaoUserResponse userInfo = kakaoClient.getUserInfo(request.getAccessToken());
        User user = userRepository.findByOauthId(userInfo.getId());
        if(user == null){
            user = signUpUser(userInfo);
        }
        System.out.println("user = " + user.toString());
        String accessToken = jwtAuthTokenProvider.createAccessToken(AuthTokenPayload.of(user.getUserSeq()));
        String refreshToken = jwtAuthTokenProvider.createRefreshToken();
        user.updateRefreshToken(refreshToken);
        return LoginResponse.of(accessToken, refreshToken);
    }

    private User signUpUser(KakaoUserResponse userInfo) {
        User newUser = User.newKaKaoInstance(userInfo.getId(), userInfo.getNickName(), userInfo.getAge_range(), userInfo.getGender());
        return userRepository.save(newUser);
    }
}
