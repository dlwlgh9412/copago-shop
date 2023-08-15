package com.copago.common.infrastructer.repository.alarm;

import com.copago.common.entity.alarm.AlarmUserEntity;
import com.copago.common.entity.alarm.AlarmType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlarmUserRepository extends JpaRepository<AlarmUserEntity, Long> {
    Optional<AlarmUserEntity> findByChatIdAndType(String chatId, AlarmType type);
}
