package com.copago.common.infrastructer.client.coupang.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoupangReturnMResponse {
    private List<CoupangReturnMVendorItem> vendorItems;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangReturnMVendorItem {
        private Map<String, CoupangReturnMVendorItemBadge> badgeMap;
        private Object delivery;
        private Long itemId;
        private Long productId;
        private Long vendorItemId;
        private CoupangReturnMVendorItemPrice price;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangReturnMVendorItemBadge {
        private String type;
        private String text;
        private String helpLink;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangReturnMVendorItemPrice {
        private String couponPrice;
        private String salePrice;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangMData {
        private CoupangMProduct product;
        private CoupangMVendorItemDetail vendorItemDetail;
        private CoupangMPanelContainer panelContainerVo;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangMProduct {
        private Data productDto;

        @Getter
        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static class Data {
            private Long productId;
            private Long categoryId;
            private String brand;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangMVendorItemDetail {
        private Data item;
        private CoupangMResource resource;

        @Getter
        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static class Data {
            private Long productId;
            private Long itemId;
            private String itemName;
            private Long vendorItemId;
            private String productName;
            private Long originalPrice;
            private Long couponPrice;
            private Boolean soldOut;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangMResource {
        private Map<String, Object> originalSquare;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangMPanelContainer {
        private Object header;
        private List<CoupangMPanelContainerContent1> panelList;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangMPanelContainerContent1 {
        private List<CoupangMPanelContainerContent2> contents;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangMPanelContainerContent2 {
        private List<CoupangMPanelContainerContent3> contents;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangMPanelContainerContent3 {
        private List<Map<String, Object>> title;
    }
}
