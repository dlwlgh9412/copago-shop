package com.copago.api.config.web;

import com.copago.api.web.dto.request.AuthenticationUser;
import com.copago.api.utils.JwtTokenUtils;
import com.copago.common.entity.admin.AdminUserEntity;
import com.copago.common.infrastructer.repository.admin.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthenticationTokenResolver implements HandlerMethodArgumentResolver {
    private final JwtTokenUtils jwtTokenUtils;
    private final AdminUserRepository adminUserRepository;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == AuthenticationUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = getToken(webRequest);
        String userName = jwtTokenUtils.getUsername(token);

        if (!StringUtils.hasText(userName)) throw new IllegalAccessException();

        AdminUserEntity user = adminUserRepository.findByUserId(userName).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        return new AuthenticationUser(user.getId(), user.getUserName(), token, user.getUserId());
    }

    private String getToken(NativeWebRequest request) {
        String header = request.getHeader("Authentication");
        if (StringUtils.hasText(header))
            return header.substring(7);
        throw new IllegalStateException();
    }
}
