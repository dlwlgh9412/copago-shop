package com.copago.api.service;

import com.copago.api.web.dto.response.CategoryResponse;
import com.copago.common.entity.brand.BrandEntity;
import com.copago.common.entity.category.CategoryEntity;
import com.copago.common.infrastructer.repository.brand.BrandRepository;
import com.copago.common.infrastructer.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public List<CategoryResponse> getCategories() {
        return categoryRepository.findByIsEnabledTrue().stream().map(CategoryResponse::new).collect(Collectors.toList());
    }

    public List<CategoryResponse> getCategories(String categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        return categoryRepository.findByCategoryStartingWithAndIsEnabledTrue(category.getCategory())
                .stream().map(CategoryResponse::new).collect(Collectors.toList());
    }

    public List<CategoryResponse> getCategoriesByBrand(String brandName) {
        BrandEntity brand = brandRepository.findById(brandName).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 브랜드입니다."));
        return categoryRepository.findByCategoryInAndIsEnabledTrue(brand.getCategories()).stream().map(CategoryResponse::new).collect(Collectors.toList());
    }
}
