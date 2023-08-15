package com.copago.common.entity.product;

import com.copago.common.entity.AbstractBaseEntity;
import com.copago.common.utils.EntityUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "tb_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "category")
    private String category;

    @Column(name = "coupang_id")
    private String coupangId;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "vendor_id")
    private String vendorId;

    @Column(name = "image")
    private String image;

    @Column(name = "title")
    private String title;

    @Column(name = "model")
    private String model;

    @Column(name = "detail")
    private String detail;

    @Column(name = "price")
    private Long price;

    @Column(name = "sale")
    private Long sale;

    @Column(name = "card_info")
    private String cardInfo;

    @Column(name = "origin_price")
    private Long originPrice;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "telegram")
    private Boolean telegram = false;

    @Column(name = "url")
    private String url;

    @Column(name = "is_new")
    private Boolean isNew = true;

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setOriginPrice(Long originPrice) {
        this.originPrice = originPrice;
    }

    public void setCardInfo(String cardInfo) {
        this.cardInfo = cardInfo;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public void setSale(Long sale) {
        this.sale = sale;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getCardPrice() {
        return EntityUtils.getCardPrice(this.cardInfo, this.price);
    }

    public Long getSalePercent() {
        return EntityUtils.getSalePercent(cardInfo, price, originPrice);
    }
}
