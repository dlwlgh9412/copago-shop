package com.copago.common.infrastructer.client.coupang.dto.request;

import lombok.*;

import java.util.List;

public class CoupangPartnerRequest {
    @Getter
    @RequiredArgsConstructor
    public static class Product {
        private final String type = "PRODUCT";
        private final Long itemId;
        private final Long productId;
        private final Long vendorItemId;
        private final String image;
        private final String title;
        private Long originPrice;
        private Long salesPrice;
        private final String deliveryBadgeImage = "https://image10.coupangcdn.com/image/cmg/icon/ios/logo_rocket_large@3x.png";
        private Long discountRate;

        public Product(Long itemId, Long productId, Long vendorItemId, String image, String title, Long originPrice, Long salesPrice, Long discountRate) {
            this.itemId = itemId;
            this.productId = productId;
            this.vendorItemId = vendorItemId;
            this.image = image;
            this.title = title;
            this.originPrice = originPrice;
            this.salesPrice = salesPrice;
            this.discountRate = discountRate;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DeepLink {
        private List<String> coupangUrls;
        private String subId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Auth {
        private String password;
    }
}
