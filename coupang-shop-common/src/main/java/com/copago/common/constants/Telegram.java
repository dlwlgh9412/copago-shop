package com.copago.common.constants;

import lombok.Getter;

@Getter
public enum Telegram {
    COPAGO("-1001855904564");

    private final String chatId;

    Telegram(String chatId) {
        this.chatId = chatId;
    }
}
