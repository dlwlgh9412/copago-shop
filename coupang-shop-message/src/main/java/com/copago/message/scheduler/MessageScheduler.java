package com.copago.message.scheduler;

import com.copago.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageScheduler {
    private final MessageService messageService;

    @Scheduled(fixedDelay = 1000, initialDelay = 1000 * 5)
    public void sendMessage() {
        messageService.sendMessage();
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 1000 * 10)
    public void sendMessageByFailed() {
        messageService.sendMessageByFailed();
    }
}
