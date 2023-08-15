package com.copago.scheduler.scheduler;

import com.copago.common.entity.product.ProductEntity;
import com.copago.common.infrastructer.repository.product.ProductRepository;
import com.copago.common.utils.Extensions;
import com.copago.scheduler.service.ProductTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductScheduler {
    private final ProductRepository productRepository;
    private final ProductTrackingService productTrackingService;

    @Scheduled(fixedDelay = 1000 * 60 * 10, initialDelay = 1000 * 7)
    public void updateForWeb() {
        List<ProductEntity> productList = productRepository.findAllByIsEnabledTrue();
        int chunkSize = 200;

        List<List<ProductEntity>> chunkProductList = Extensions.convertToChunkedList(chunkSize, productList);

        chunkProductList.forEach(chunk -> {
            productTrackingService.searchBatch(chunk, false);
        });
    }

    @Scheduled(fixedDelay = 1000 * 30, initialDelay = 1000 * 10)
    public void searchBatchByReturnItem() {
        List<ProductEntity> productList = productRepository.findAllByTelegramTrue();
        int chunkSize = 30;

        List<List<ProductEntity>> chunkProductList = Extensions.convertToChunkedList(chunkSize, productList);

        chunkProductList.forEach(chunk -> {
            productTrackingService.searchBatch(chunk, true);
        });
    }
}
