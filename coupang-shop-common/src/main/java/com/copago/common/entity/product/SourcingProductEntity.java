package com.copago.common.entity.product;

import com.copago.common.entity.AbstractBaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ReflectionUtils;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Getter
@Table
@Entity(name = "tb_sourcing_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SourcingProductEntity extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "brand")
    private String brand = null;

    @Column(name = "coupang_id")
    private String coupangId;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "vendor_id")
    private String vendorId;

    @Column(name = "price")
    private Long price = 0L;

    @Column(name = "origin_price")
    private Long originPrice;

    @Column(name = "title")
    private String title;

    @Column(name = "detail")
    private String detail;

    @Column(name = "model")
    private String model;

    @Column(name = "image")
    private String image;

    @Column(name = "card_info")
    private String cardInfo = null;

    @Column(name = "is_enabled")
    private Boolean isEnabled = false;

    @Column(name = "telegram")
    private Boolean telegram = false;

    @Column(name = "is_use")
    private Boolean isUse = false;

    @Builder
    public SourcingProductEntity(String category, String brand, String coupangId, String itemId, String vendorId, Long price, Long originPrice, String title, String detail, String model, String image, String cardInfo, Boolean isEnabled, Boolean telegram, Boolean isUse) {
        this.category = category;
        this.brand = brand;
        this.coupangId = coupangId;
        this.itemId = itemId;
        this.vendorId = vendorId;
        this.price = price;
        this.originPrice = originPrice;
        this.title = title;
        this.detail = detail;
        this.model = model;
        this.image = image;
        this.cardInfo = cardInfo;
        this.isEnabled = isEnabled;
        this.telegram = telegram;
        this.isUse = isUse;
    }

    public void setModifiedAtNow() {
        Field modifiedAtField = ReflectionUtils.findField(AbstractBaseEntity.class, "modifiedAt");

        if (modifiedAtField != null) {
            ReflectionUtils.makeAccessible(modifiedAtField);
            ReflectionUtils.setField(modifiedAtField, this, LocalDateTime.now());
        }
    }

    public void setPrice(Long price) {
        this.price = price;
    }
    public void setCardInfo(String cardInfo) {
        this.cardInfo = cardInfo;
    }

    public void setUse() {
        this.isUse = true;
    }
}
