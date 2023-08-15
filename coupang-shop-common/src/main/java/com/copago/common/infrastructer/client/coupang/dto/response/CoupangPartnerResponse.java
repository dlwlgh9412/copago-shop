package com.copago.common.infrastructer.client.coupang.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoupangPartnerResponse<T> {
    private String rCode;
    private String rMessage;
    private T data;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ShortenUrl {
        private String originUrl;
        private String shortenUrl;
        private String landingUrl;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Commission {
        private String date;
        private String trackingCode;
        private String sudId;
        private Long commission;
    }
}

