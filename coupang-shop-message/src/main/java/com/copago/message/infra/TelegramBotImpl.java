package com.copago.message.infra;

import com.copago.common.constants.Telegram;
import com.copago.message.service.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class TelegramBotImpl extends TelegramLongPollingBot implements TelegramBot {
    @Value("${telegram.token}")
    private String token;

    private final CommandService commandService;

    @Override
    public String getBotUsername() {
        return "Copago-Shop";
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String message = commandService.command(update.getMessage().getText(), chatId);

        if (StringUtils.hasText(message)) {
            try {
                sendMessage(message, chatId);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void sendMessage(String data, String channelId) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.enableHtml(true);
        message.setParseMode("HTML");
        message.setChatId(StringUtils.hasText(channelId) ? channelId : Telegram.COPAGO.getChatId());
        message.setText(data);
        execute(message);
    }
}
