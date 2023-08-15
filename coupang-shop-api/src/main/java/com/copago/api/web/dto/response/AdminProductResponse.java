package com.copago.api.web.dto.response;

import com.copago.common.entity.brand.BrandEntity;
import com.copago.common.entity.category.CategoryEntity;
import com.copago.common.entity.product.ProductEntity;
import lombok.Getter;

@Getter
public class AdminProductResponse {
    private final Long id;
    private final String name;
    private final String detail;
    private final Long price;
    private final String cardInfo;
    private final String url;
    private final String image;
    private final String category;
    private final String categoryName;
    private final String brand;
    private final String brandName;
    private final Boolean isTelegram;
    private final Boolean enabled;

    public AdminProductResponse(ProductEntity product, CategoryEntity category, BrandEntity brand) {
        this.id = product.getId();
        this.name = product.getTitle();
        this.detail = product.getDetail();
        this.price = product.getPrice();
        this.cardInfo = product.getCardInfo();
        this.url = product.getUrl();
        this.image = product.getImage();
        this.category = category != null ? category.getCategory() : null;
        this.categoryName = category != null ? category.getName() : null;
        this.brand = brand != null ? brand.getId() : null;
        this.brandName = brand != null ? brand.getName() : null;
        this.isTelegram = product.getTelegram();
        this.enabled = product.getIsEnabled();
    }
}
