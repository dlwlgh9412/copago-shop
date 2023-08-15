package com.copago.message.service;

import com.copago.common.entity.alarm.AlarmType;
import com.copago.common.entity.alarm.AlarmUserEntity;
import com.copago.common.infrastructer.repository.alarm.AlarmUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandServiceImpl implements CommandService {
    @Value("${coupang-shop.url}")
    private String url;

    private final AlarmUserRepository alarmUserRepository;
    private final ProductCommandService productCommandService;

    @Override
    public String command(String text, String chatId) {
        String[] textParts = text.split(" ");
        switch (textParts[0]) {
            case "/start":
                if (alarmUserRepository.findByChatIdAndType(chatId, AlarmType.TELEGRAM).isPresent()) {
                    alarmUserRepository.save(new AlarmUserEntity(chatId, AlarmType.TELEGRAM, 0L));
                }
                return getHelpMessage();

            case "/명령어":
                return getHelpMessage();
            case "/상품추가":
                return productCommandService.addProduct(chatId, textParts[1], textParts[2]);
            case "/상품보기":
                return selectProductsMessage(chatId);
            default:
                return "명령어를 확인해주세요.";
        }
    }

    private String getHelpMessage() {
        return "<b>안녕하세요. 오늘의 득템 (쿠팡)알람봇입니다.</b>\n" +
                "여러분들이 알림 받고 싶어하시는 상품의 URL을 입력해주시면 상품을 추적하여 여러분들께 가격이 변할 시 알람을 보내드립니다.\n" +
                "현재 제공되는 명령어는 아래와 같습니다.\n" +
                "[명령어]\n" +
                "<b>/명령어</b>\n" +
                "- 명령어 리스트 설명\n" +
                "<b>/상품추가 [URL] [가격]</b>\n" +
                "- 특정 상품에 대한 가격을 설정해주시면 해당 가격 범위에거 가격이 변동 시 알람을 드립니다. ([베타]최대 100개)\n" +
                "<b>/상품보기</b>\n" +
                "- 현재까지 등록하신 상품들을 보여드립니다.";
    }

    private String selectProductsMessage(String chatId) {
        return url + "/alarm/" + chatId + "\n" +
                "|\n" +
                "|본인의 링크에서 상품 수정 및 삭제가 가능함으로 타인에게 링크를 공유하지 마세요.";
    }
}
