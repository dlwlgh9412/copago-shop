package com.copago.message.infra;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramBot {
    void sendMessage(String data, String channelId) throws TelegramApiException;
}
