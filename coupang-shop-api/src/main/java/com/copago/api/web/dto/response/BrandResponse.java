package com.copago.api.web.dto.response;

import com.copago.common.entity.brand.BrandEntity;
import lombok.Getter;

@Getter
public class BrandResponse {
    private final String id;
    private final String name;

    public BrandResponse(BrandEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
