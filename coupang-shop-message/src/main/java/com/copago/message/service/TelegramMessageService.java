package com.copago.message.service;

import com.copago.common.entity.alarm.AlarmProductEntity;
import com.copago.common.entity.message.MessageEntity;
import com.copago.common.entity.message.MsgStatus;
import com.copago.common.infrastructer.repository.message.MessageRepository;
import com.copago.common.utils.Extensions;
import com.copago.message.infra.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final TelegramBot telegramBot;


    @Override
    @Transactional
    public void sendMessageByFailed() {
        List<MessageEntity> messageList = messageRepository.findByStatus(MsgStatus.FAILED);
        messageList.forEach(it -> {
            try {
                telegramBot.sendMessage(it.getMessage(), it.getChannelId());
                it.setStatus(MsgStatus.SENT);
            } catch (TelegramApiException e) {
                it.setStatus(MsgStatus.FAILED);
            }
        });
    }

    @Override
    @Transactional
    public void sendMessage() {
        messageRepository.findByStatus(MsgStatus.READY).forEach(it -> {
            try {
                telegramBot.sendMessage(it.getMessage(), it.getChannelId());
                it.setStatus(MsgStatus.SENT);
            } catch (TelegramApiException e) {
                it.setStatus(MsgStatus.FAILED);
            }
        });
    }

    @Override
    public String makeMessageByAlarmProduct(AlarmProductEntity product, Long cardPrice) {
        return "<b>" + product.getTitle() + "</b>\n" +
                "카드할인: " + (StringUtils.hasText(product.getCardInfo()) ? product.getCardInfo() : "없음") + "\n" +
                "가격: " + Extensions.toMoney(product.getOriginalPrice().toString()) + "원\n" +
                "최저가: <b>" + Extensions.toMoney(product.getPrice().toString()) + "</b>원\n" +
                "카드할인가: <b>" + Extensions.toMoney(product.getCardPrice().toString()) + "</b>원\n" +
                "정보: " + product.getUrl();
    }
}
