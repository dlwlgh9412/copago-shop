package com.copago.scheduler.web.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HealthResponse {
    private String application;
    private List<String> profiles;
    private String health;
    private LocalDateTime dateTime;
}
