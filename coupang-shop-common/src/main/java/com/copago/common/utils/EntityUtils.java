package com.copago.common.utils;

import org.springframework.util.StringUtils;

public class EntityUtils {
    public static Long getCardPrice(String cardInfo, Long price) {
        if (StringUtils.hasText(cardInfo)) {
            Long percent = Extensions.removeToLong(cardInfo);
            return price * (100 - percent) / 100;
        }

        return 0L;
    }

    public static Long getSalePercent(String cardInfo, Long price, Long originPrice) {
        if (price == null || originPrice == null) return 0L;
        if (!StringUtils.hasText(cardInfo))
            return 100 * (originPrice - price) / originPrice;
        else
            return 100 * (originPrice - getCardPrice(cardInfo, price)) / originPrice;
    }
}
