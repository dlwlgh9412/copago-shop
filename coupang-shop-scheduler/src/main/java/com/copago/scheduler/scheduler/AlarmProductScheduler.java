package com.copago.scheduler.scheduler;

import com.copago.scheduler.service.AlarmProductTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmProductScheduler {
    private final AlarmProductTrackingService AlarmProductTrackingService;

    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void updateByNewProduct() {
        AlarmProductTrackingService.updateByNewProduct();
    }

    @Scheduled(cron = "30 * * * * *", zone = "Asia/Seoul")
    public void update() {
        AlarmProductTrackingService.update();
    }
}
