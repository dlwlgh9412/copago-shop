package com.copago.scheduler.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductReturnResponse {
    private String title;
    private String model;
    private String detail;
    private String coupangPrice;
    private String salePrice;
    private String url;
    private String date;
    private String cardInfo;
    private String cardPrice;
    private String status;
}
