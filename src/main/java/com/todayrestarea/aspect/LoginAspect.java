package com.todayrestarea.aspect;

import com.todayrestarea.auth.jwt.JwtAuthTokenProvider;
import com.todayrestarea.common.dto.BaseException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.todayrestarea.common.ErrorCode.NOT_FOUND_TOKEN_EXCEPTION;

@Aspect
@Component
@RequiredArgsConstructor
public class LoginAspect {

    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    @Around("execution(* *(.., @com.todayrestarea.aspect.UserId (*), ..))")
    public Object authenticateWithToken(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request =requestAttributes.getRequest();

        String token = request.getHeader("Authorization").substring(7);
        
        if(!StringUtils.hasText(token)) {
            throw new BaseException(NOT_FOUND_TOKEN_EXCEPTION);
        }

        CodeSignature codeSignature = (CodeSignature) pjp.getSignature();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] args = pjp.getArgs();

        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals("userId")) {
                args[i] = jwtAuthTokenProvider.getPayload(token).getUserId();
            }
        }

        return pjp.proceed(args);
    }
}
