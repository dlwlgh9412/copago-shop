package com.copago.common.infrastructer.client.coupang.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoupangProductResponse<T> {
    private String rCode;
    private String rMessage;
    private T rData;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Product {
        private List<CoupangProductEntityList> products;
        private Long matchedCnt;
        private String nextPageKey;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangProductEntityList {
        private String type;
        private Object entity;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangBrandAdProduct {
        private String viewType;
        private Object mainCreative;
        private List<CoupangBrandAdProductSubCreative> subCreatives;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangBrandAdProductSubCreative {
        private String type;
        private List<CoupangProductFont> titleAttributes;
        private List<CoupangProductFont> subTitleAttributes;
        private CoupangProductDisplayItem displayItem;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangProductEntity {
        private CoupangProductDisplayItem displayItem;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangProductDisplayItem {
        private String id;
        private String itemId;
        private String itemProductId;
        private String vendorItemId;
        private String vendorId;
        private String searchId;
        private String searchKeyword;
        private String title;
        private String itemName;
        private String thumbnailSquare;
        private List<Object> morThumbnailList;
        private Boolean adultProduct;
        private Boolean soldOut;
        private Boolean subscribable;
        private Object logging;
        private Object eligibleFalcon;
        private Object stockRemainingInfo;
        private Long snsDiscountRate;
        private Long wowOnlyInstantDiscountRate;
        private List<Object> personalizedBenefits;
        private String divisionType;
        private Boolean bestPrice;
        private List<String> originalPrice;
        private List<String> salesPrice;
        private List<String> lowestPrice;
        private String discountRate;
        private List<CoupangProductFont> usedProductInfo;
        private List<CoupangProductFont> usedProductPrice;
        private List<CoupangProductFont> promiseDeliveryDate;
        private List<CoupangProductFont> attributedTitle;
        private List<CoupangProductFont> modelNumber;
        private CoupangProductBackInfo ccidInfo;
        private Boolean ccidEligible;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangProductBackInfo {
        private String badgeIconUrl;
        private List<CoupangProductFont> badgeText;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangProductFont {
        private String text;
        private String color;
        private Boolean bold;
        private Boolean strikethrough;
        private Boolean dip;
        private Long size;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangUsedItemNudgeData {
        private CoupangUsedItemNudge usedItemNudge;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangUsedItemNudge {
        private Object header;
        private Object footerButton;
        private List<CoupangOfferItem> offerItmes;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangOfferItem {
        private List<Object> usedStatusName;
        private CoupangOfferItemAction action;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangOfferItemAction {
        private String type;
        private CoupangOfferItemActionData data;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CoupangOfferItemActionData {
        private String scheme;
    }
}
