package com.todayrestarea.user.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.todayrestarea.user.util.jwt.dto.AuthTokenPayload;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtAuthTokenProvider {

    private static final String JWT_SECRET = "secretKey";

    private static final int JWT_EXPIRATION_MS = 604800000;


    public String createAccessToken(AuthTokenPayload payload) {
        try {
            Date now = new Date();
            Date expiresAt = new Date(now.getTime() + JWT_EXPIRATION_MS);

            return JWT.create()
                    .withIssuer("test")
                    .withClaim("user_seq", payload.getUserSeq())
                    .withIssuedAt(now)
                    .withExpiresAt(expiresAt)
                    .sign(Algorithm.HMAC256(JWT_SECRET));
        } catch (JWTCreationException exception) {
            throw new IllegalArgumentException("액세스 토큰을 만드는 중 에러가 발생하였습니다");
        }
    }

    public AuthTokenPayload getPayload(String accessToken) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET))
                .withIssuer("test")
                .build();

        try {
            DecodedJWT jwt = verifier.verify(accessToken);
            return AuthTokenPayload.of(jwt.getClaim("user_seq").asLong());
        }  catch (TokenExpiredException exception) {
            throw new TokenExpiredException("Access token($accessToken)이 만료되었습니다.");
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Access token($accessToken)을 디코드 하는 중 에러가 발생하였습니다. message: ${exception.message}");
        }
    }

    public String createRefreshToken() {
        try {
            Date now = new Date();
            Date expiresAt = new Date(now.getTime() + JWT_EXPIRATION_MS);

            return JWT.create()
                    .withIssuer("test")
                    .withIssuedAt(now)
                    .withExpiresAt(expiresAt)
                    .sign(Algorithm.HMAC256(JWT_SECRET));
        } catch (JWTCreationException exception) {
            throw new IllegalArgumentException("refresh 토큰을 만드는 중 에러가 발생하였습니다");
        }
    }
}
