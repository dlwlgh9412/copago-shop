package com.copago.api.web.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminProductAddRequest {
    private String coupangId;
    private String itemId;
    private String vendorId;
    private Boolean isTelegram;
    private Boolean enabled;
    private String category;
    private String brand;
}
