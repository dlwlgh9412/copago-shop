package com.copago.message.scheduler;

import com.copago.message.service.AlarmTargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmTargetScheduler {
    private final AlarmTargetService alarmTargetService;

    @Scheduled(fixedDelay = 1000 * 10, initialDelay = 1000 * 10)
    public void makeMessage() {
        alarmTargetService.makeMessage();
    }

}
