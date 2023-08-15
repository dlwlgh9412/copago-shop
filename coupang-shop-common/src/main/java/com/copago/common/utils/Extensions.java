package com.copago.common.utils;

import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Extensions {
    public static String toDateTimeString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static Long removePercentToLong(String str) {
        return StringUtils.hasText(str) ? Long.parseLong(str.replace("%", "")) : 0L;
    }

    public static Long removeDotToLong(String str) {
        try {
            return Long.parseLong(str.replace(",", ""));
        } catch (Exception e) {
            return null;
        }
    }

    public static String toMoney(String str) {
        DecimalFormat format = new DecimalFormat("#,###");
        try {
            if (StringUtils.hasText(str))
                return format.format(Long.parseLong(str));
            return "-";
        } catch (Exception e) {
            return "";
        }
    }

    public static Long removeToLong(String str) {
        return Long.parseLong(str.replaceAll("\\D", ""));
    }

    public static Long getCardPrice(String cardInfo, Long price) {
        Long percent = Extensions.removeToLong(cardInfo);
        return price * (100 - percent) / 100;
    }

    public static Long getDiscountPercent(String cardInfo, Long price, Long originPrice) {
        if (price == null || originPrice == null) return 0L;
        if (!StringUtils.hasText(cardInfo))
            return 100 * (originPrice - price) / originPrice;
        else
            return 100 * (originPrice - getCardPrice(cardInfo, price)) / originPrice;
    }

    public static <T> List<List<T>> convertToChunkedList(int chunkSize, List<T> target) {
        List<List<T>> result = new ArrayList<>();

        for (int i = 0; i < target.size(); i += chunkSize) {
            result.add(target.subList(i, Math.min(i + chunkSize, target.size())));
        }

        return result;
    }
}
