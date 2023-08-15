package com.copago.common.infrastructer.repository.alarm;

import com.copago.common.entity.alarm.AlarmTargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmTargetRepository extends JpaRepository<AlarmTargetEntity, Long> {
    List<AlarmTargetEntity> findAllByIsSend(Boolean isSend);
}
