package com.copago.message.service;

import com.copago.common.entity.alarm.AlarmProductEntity;

public interface MessageService {
    String makeMessageByAlarmProduct(AlarmProductEntity product, Long cardPrice);
    void sendMessageByFailed();
    void sendMessage();
}
