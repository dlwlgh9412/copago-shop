package com.copago.api.service;

import com.copago.api.web.dto.response.AdminSourcingProductResponse;
import com.copago.common.entity.product.SourcingProductEntity;
import com.copago.common.infrastructer.repository.product.SourcingProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminSourcingProductService {
    private final SourcingProductRepository sourcingProductRepository;

    public Page<AdminSourcingProductResponse> getSourcingProducts(String start, String end, Integer page, Integer size) {
        LocalDateTime startDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return sourcingProductRepository.findAllByCreatedAtBetween(startDate, endDate, pageRequest)
                .map(AdminSourcingProductResponse::new);
    }

    public AdminSourcingProductResponse getSourcingProduct(Long sourcingId) {
        SourcingProductEntity sourcingProduct = sourcingProductRepository.findById(sourcingId).orElseThrow(() -> new IllegalArgumentException("잘못된 상품 ID 입니다."));
        return new AdminSourcingProductResponse(sourcingProduct);
    }
}
