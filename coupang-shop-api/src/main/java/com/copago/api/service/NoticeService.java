package com.copago.api.service;

import com.copago.api.web.dto.response.NoticeResponse;
import com.copago.common.entity.notice.NoticeType;
import com.copago.common.infrastructer.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public List<NoticeResponse> getNotice(NoticeType type) {
        LocalDateTime now = LocalDateTime.now();

        return noticeRepository.findByStartDateLessThanAndEndDateGreaterThanAndTypeOrderByPriority(now, now, type).stream().map(NoticeResponse::new).collect(Collectors.toList());
    }
}
