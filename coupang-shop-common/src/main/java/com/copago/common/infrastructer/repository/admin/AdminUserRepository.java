package com.copago.common.infrastructer.repository.admin;

import com.copago.common.entity.admin.AdminUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<AdminUserEntity, Long> {
    Optional<AdminUserEntity> findByUserIdAndPassword(String userId, String password);
    Optional<AdminUserEntity> findByUserId(String userId);
}
