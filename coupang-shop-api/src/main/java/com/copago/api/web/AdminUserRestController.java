package com.copago.api.web;

import com.copago.api.web.dto.request.AuthenticationUser;
import com.copago.api.service.AdminUserService;
import com.copago.api.web.dto.request.AdminLoginRequest;
import com.copago.api.web.dto.response.AdminLoginResponse;
import com.copago.api.web.dto.response.AdminSessionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
public class AdminUserRestController {
    private final AdminUserService adminUserService;

    @GetMapping("/session")
    public ResponseEntity<AdminSessionResponse> session(AuthenticationUser user) {
        return ResponseEntity.ok(adminUserService.session(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(@RequestBody AdminLoginRequest request) {
        return ResponseEntity.ok(adminUserService.login(request));
    }
}
