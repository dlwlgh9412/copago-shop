package com.copago.api.service;

import com.copago.api.web.dto.response.BrandResponse;
import com.copago.common.infrastructer.repository.brand.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BrandService {
    private final BrandRepository brandRepository;

    public List<BrandResponse> getBrands(String category) {
        if (!StringUtils.hasText(category)) {
            return brandRepository.findAll().stream().map(BrandResponse::new).collect(Collectors.toList());
        } else {
            return brandRepository.findByCategoriesContaining(category).stream().map(BrandResponse::new).collect(Collectors.toList());
        }
    }
}
