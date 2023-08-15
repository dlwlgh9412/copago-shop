package com.copago.api.web.dto.response;

import com.copago.common.entity.product.ProductEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponse {
    private Long id;
    private String name; // 상품명
    private String detail; // 상품 상세 스펙
    private Long price; // 쿠팡 할인가
    private Long originPrice; // 쿠팡원가
    private String cardInfo; // 카드 즉시 할인률
    private Long cardPrice; // 쿠팡 할인가 + 카드 즉시 할인
    private String image;
    private Long sale;
    private String url;

    public ProductResponse(ProductEntity entity) {
        this.id = entity.getId();
        this.name = entity.getTitle();
        this.detail = entity.getDetail();
        this.price = entity.getPrice();
        this.originPrice = entity.getOriginPrice();
        this.cardInfo = entity.getCardInfo();
        this.cardPrice = entity.getCardPrice();
        this.image = entity.getImage();
        this.sale = entity.getSale();
        this.url = "/re/AFFSDP?lptag=AF3240366&pageKey=" + entity.getCoupangId() + "&traceid=V0-101-ac7db92330e7e32c";
    }
}
