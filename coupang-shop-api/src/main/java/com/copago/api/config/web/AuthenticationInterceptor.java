package com.copago.api.config.web;

import com.copago.api.exception.UnauthorizedException;
import com.copago.api.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = getToken(request);
        jwtTokenUtils.getClaim(token);
        return true;
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authentication");
        if (StringUtils.hasText(header))
            return header.substring(7);
        throw new UnauthorizedException("토큰 값이 존재하지 않습니다.");
    }
}
