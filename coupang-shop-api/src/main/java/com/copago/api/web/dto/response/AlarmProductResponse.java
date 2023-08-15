package com.copago.api.web.dto.response;

import com.copago.common.entity.alarm.AlarmProductEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlarmProductResponse {
    private Long id;
    private String title;
    private Long price;
    private Long originPrice;
    private String cardInfo;
    private Long cardPrice;
    private String image;
    private Long sale;
    private String url;
    private Long userPrice;
    private Boolean soldOut;

    public AlarmProductResponse(AlarmProductEntity entity, Long cardPrice, Long userPrice) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.price = entity.getPrice();
        this.originPrice = entity.getOriginalPrice();
        this.cardInfo = entity.getCardInfo();
        this.image = entity.getImage();
        this.cardPrice = cardPrice;
        this.userPrice = userPrice;
        this.sale = entity.getSale();
        this.soldOut = entity.getIsSoldOut();
        this.url = "/re/AFFSDP?lptag=AF3240366&pageKey=" + entity.getCoupangId() + "&traceid=V0-101-ac7db92330e7e32c";

    }
}
