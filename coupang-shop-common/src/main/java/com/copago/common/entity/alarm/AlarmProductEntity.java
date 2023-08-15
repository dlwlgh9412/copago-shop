package com.copago.common.entity.alarm;

import com.copago.common.entity.AbstractBaseEntity;
import com.copago.common.infrastructer.client.coupang.dto.response.CoupangPartnerResponse;
import com.copago.common.infrastructer.client.coupang.dto.response.CoupangReturnMResponse;
import com.copago.common.utils.EntityUtils;
import com.copago.common.utils.Extensions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "tb_alarm_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmProductEntity extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupang_id")
    private String coupangId;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "vendor_id")
    private String vendorId;

    @Column(name = "brand")
    private String brand = null;

    @Column(name = "category")
    private Long category = null;

    @Column(name = "image")
    private String image = null;

    @Column(name = "title")
    private String title = null;

    @Column(name = "price")
    private Long price = null;

    @Column(name = "sale")
    private Long sale = null;

    @Column(name = "is_sold_out")
    private Boolean isSoldOut = true;

    @Column(name = "card_info")
    private String cardInfo = null;

    @Column(name = "card_discount")
    private Long cardSale = null;

    @Column(name = "origin_price")
    private Long originalPrice = null;

    @Column(name = "url")
    private String url = null;

    @Column(name = "is_new")
    private Boolean isNew = true;

    public AlarmProductEntity(String coupangId, String itemId, String vendorId) {
        this.coupangId = coupangId;
        this.itemId = itemId;
        this.vendorId = vendorId;
    }

    public void updateByNewProduct(CoupangReturnMResponse.CoupangMData data, CoupangPartnerResponse<List<CoupangPartnerResponse.ShortenUrl>> deepLinkResponse) {
        this.vendorId = data.getVendorItemDetail().getItem().getVendorItemId().toString();
        this.itemId = data.getVendorItemDetail().getItem().getItemId().toString();
        this.title = data.getVendorItemDetail().getItem().getItemName();
        this.originalPrice = data.getVendorItemDetail().getItem().getOriginalPrice();
        this.price = data.getVendorItemDetail().getItem().getCouponPrice();
        this.cardInfo = getCardInfo(data);
        this.cardSale = getCardSale(this.cardInfo);
        this.sale = getSalePercent();
        this.image = data.getVendorItemDetail().getResource().getOriginalSquare().get("url").toString();
        this.brand = data.getProduct().getProductDto().getBrand();
        this.category = data.getProduct().getProductDto().getCategoryId();
        this.isSoldOut = data.getVendorItemDetail().getItem().getSoldOut();
        this.isNew = false;
        this.url = getUrlByDeepLinkResponse(deepLinkResponse);
    }

    public void update(CoupangReturnMResponse.CoupangMData data) {
        Long price = data.getVendorItemDetail().getItem().getCouponPrice();
        Boolean isSoldOut = data.getVendorItemDetail().getItem().getSoldOut();

        if (this.price != price || this.isSoldOut != isSoldOut) {
            this.originalPrice = data.getVendorItemDetail().getItem().getOriginalPrice();
            this.price = price;
            this.cardInfo = getCardInfo(data);
            this.cardSale = getCardSale(this.cardInfo);
            this.isSoldOut = data.getVendorItemDetail().getItem().getSoldOut();
            this.sale = this.getSalePercent();
        }
    }

    public void updateUrl(CoupangPartnerResponse<List<CoupangPartnerResponse.ShortenUrl>> deepLinkResponse) {
        if (!StringUtils.hasText(this.url)) {
            this.url = getUrlByDeepLinkResponse(deepLinkResponse);
        }
    }

    private String getCardInfo(CoupangReturnMResponse.CoupangMData data) {
        String result = null;
        if (data.getPanelContainerVo() != null && data.getPanelContainerVo().getPanelList() != null && !data.getPanelContainerVo().getPanelList().isEmpty()) {
            CoupangReturnMResponse.CoupangMPanelContainerContent1 content1 = data.getPanelContainerVo().getPanelList().get(0);
            if (content1 != null && !content1.getContents().isEmpty()) {
                CoupangReturnMResponse.CoupangMPanelContainerContent2 content2 = content1.getContents().get(0);
                if (content2 != null && !content2.getContents().isEmpty()) {
                    CoupangReturnMResponse.CoupangMPanelContainerContent3 content3 = content2.getContents().get(0);
                    if (content3 != null && !content3.getTitle().isEmpty()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        content3.getTitle().forEach(title -> {
                            Object text = title.get("text");
                            if (text != null) stringBuilder.append(text);
                        });
                        result = stringBuilder.toString();
                    }
                }
            }
        }
        return result;
    }

    private String getUrlByDeepLinkResponse(CoupangPartnerResponse<List<CoupangPartnerResponse.ShortenUrl>> deepLinkResponse) {
        String url = null;
        if (deepLinkResponse != null && deepLinkResponse.getData() != null && !deepLinkResponse.getData().isEmpty()) {
            CoupangPartnerResponse.ShortenUrl shortenUrl = deepLinkResponse.getData().stream().findFirst().get();
            url = shortenUrl.getShortenUrl();
        }
        return url;
    }

    private Long getCardSale(String cardInfo) {
        if (StringUtils.hasText(cardInfo))
            return Extensions.removeToLong(cardInfo);
        return null;
    }

    public Long getCardPrice() {
        return EntityUtils.getCardPrice(this.cardInfo, this.price);
    }

    public Long getSalePercent() {
        return EntityUtils.getSalePercent(cardInfo, price, originalPrice);
    }
}
