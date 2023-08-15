package com.copago.scheduler.service;

import com.copago.common.entity.product.ProductEntity;

import java.util.List;

public interface ProductTrackingService {
    void searchBatch(List<ProductEntity> returnItems, Boolean isTelegram);
}
