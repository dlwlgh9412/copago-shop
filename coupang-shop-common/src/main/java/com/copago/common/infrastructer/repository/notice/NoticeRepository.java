package com.copago.common.infrastructer.repository.notice;

import com.copago.common.entity.notice.NoticeEntity;
import com.copago.common.entity.notice.NoticeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {
    List<NoticeEntity> findByStartDateLessThanAndEndDateGreaterThanAndTypeOrderByPriority(LocalDateTime start, LocalDateTime end, NoticeType type);
}
