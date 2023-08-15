package com.copago.api.web.dto.response;

import com.copago.common.entity.notice.NoticeEntity;
import com.copago.common.entity.notice.NoticeType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private NoticeType type;
    private Long priority;

    public NoticeResponse(NoticeEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.type = entity.getType();
        this.priority = entity.getPriority();
    }
}
