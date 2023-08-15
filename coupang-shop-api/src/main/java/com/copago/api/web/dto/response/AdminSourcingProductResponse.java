package com.copago.api.web.dto.response;

import com.copago.common.entity.product.SourcingProductEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminSourcingProductResponse {
    private Long id;
    private String title;
    private String detail;
    private Long price;
    private String cardInfo;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    private String image;
    private String url;
    private Boolean isUse;

    public AdminSourcingProductResponse(SourcingProductEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.detail = entity.getDetail();
        this.image = entity.getImage();
        this.url = "https://link.coupang.com/re/AFFSDP?lptag=AF3240366&pageKey=" + entity.getCoupangId() + "&traceid=V0-101-ac7db92330e7e32c";
        this.cardInfo = entity.getCardInfo();
        this.createdAt = entity.getCreatedAt();
        this.price = entity.getPrice();
        this.isUse = entity.getIsUse();
    }
}
