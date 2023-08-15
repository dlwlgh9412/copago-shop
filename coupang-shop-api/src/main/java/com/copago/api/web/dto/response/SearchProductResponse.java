package com.copago.api.web.dto.response;

import com.copago.common.infrastructer.client.coupang.dto.response.CoupangProductResponse;
import com.copago.common.utils.Extensions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchProductResponse {
    private String coupangId;
    private String itemId;
    private String vendorItemId;
    private String title;
    private String detail;
    private Long price;
    private Long originPrice;
    private String cardInfo;
    private Long cardPrice;
    private String image;
    private String url;

    public SearchProductResponse(CoupangProductResponse.CoupangProductDisplayItem item, String originPrice, String salePrice, String lowPrice) {
        this.coupangId = item.getId();
        this.itemId = item.getItemId();
        this.vendorItemId = item.getVendorItemId();
        this.title = item.getTitle();
        this.image = item.getThumbnailSquare();
        this.detail = item.getItemName();
        this.originPrice = (StringUtils.hasText(originPrice)) ? Long.parseLong(originPrice) : (StringUtils.hasText(salePrice)) ? Long.parseLong(salePrice) : 0L;
        this.price = (StringUtils.hasText(lowPrice)) ? Long.parseLong(lowPrice) : (StringUtils.hasText(salePrice)) ? Long.parseLong(salePrice) : (StringUtils.hasText(originPrice)) ? Long.parseLong(originPrice) : 0L;
        this.cardInfo = null;
        this.url = "/re/AFFSDP?lptag=AF3240366&pageKey=" + item.getId() + "&traceid=V0-101-ac7db92330e7e32c";
        if (item.getCcidInfo() != null && item.getCcidInfo().getBadgeText() != null) {
            Optional<CoupangProductResponse.CoupangProductFont> first = item.getCcidInfo().getBadgeText().stream().findFirst();
            first.ifPresent(coupangProductFont -> this.cardInfo = coupangProductFont.getText());
        }
        this.cardPrice = Extensions.getCardPrice(this.cardInfo, price);
    }
}
