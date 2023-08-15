package com.copago.api.service;

import com.copago.api.web.dto.request.AuthenticationUser;
import com.copago.api.utils.JwtTokenUtils;
import com.copago.api.web.dto.request.AdminLoginRequest;
import com.copago.api.web.dto.response.AdminLoginResponse;
import com.copago.api.web.dto.response.AdminSessionResponse;
import com.copago.common.entity.admin.AdminUserEntity;
import com.copago.common.infrastructer.repository.admin.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserService {
    private final AdminUserRepository adminUserRepository;
    private final JwtTokenUtils jwtTokenUtils;

    public AdminLoginResponse login(AdminLoginRequest request) {
        AdminUserEntity adminUser = adminUserRepository.findByUserId(request.getId()).orElseThrow(() -> new IllegalArgumentException("아이디 또는 패스워드가 틀렸습니다."));
        adminUser.checkPassword(request.getPassword());

        return new AdminLoginResponse(adminUser.getId(), adminUser.getUserName(), adminUser.getUserId(), jwtTokenUtils.generateToken(adminUser.getUserId()));
    }

    public AdminSessionResponse session(AuthenticationUser user) {
        AdminUserEntity adminUser = adminUserRepository.findByUserId(user.getId()).orElseThrow(() -> new IllegalArgumentException("아이디 또는 패스워드가 틀렸습니다."));

        return new AdminSessionResponse(adminUser.getId(), adminUser.getUserId(), adminUser.getUserName(), user.getToken());
    }
}
