package com.copago.api.service;

import com.copago.api.enums.SortType;
import com.copago.api.web.dto.response.ProductResponse;
import com.copago.common.entity.category.CategoryEntity;
import com.copago.common.entity.product.ProductEntity;
import com.copago.common.infrastructer.repository.category.CategoryRepository;
import com.copago.common.infrastructer.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Page<ProductResponse> getProducts(String categoryId, String title, SortType sort, String brand, Integer page, Integer size) {
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        List<String> categories = categoryRepository.findByCategoryStartingWithAndIsEnabledTrue(category.getCategory())
                .stream().map(CategoryEntity::getCategory).collect(Collectors.toList());

        Sort sortCol = Sort.by("sale").descending();
        switch (sort) {
            case NAME:
                sortCol = Sort.by("title");
                break;
            case PRICE:
                sortCol = Sort.by("price");
                break;

        }

        PageRequest pageRequest = PageRequest.of(page, size, sortCol);

        Page<ProductEntity> products;

        if (!StringUtils.hasText(brand)) brand = "";
        switch (brand) {
            case "":
                if (title == null)
                    products = productRepository.findAllByCategoryInAndPriceNotNullAndIsEnabledTrue(categories, pageRequest);
                else
                    products = productRepository.findByCategoryInAndTitleContainingAndPriceNotNullAndIsEnabledTrue(categories, title, pageRequest);
                break;
            case "APPLE":
                if (title == null)
                    products = productRepository.findAllByCategoryInAndPriceNotNullAndIsEnabledTrue(categories, pageRequest);
                else
                    products = productRepository.findByCategoryInAndTitleContainingAndPriceNotNullAndBrandContaining(categories, title, brand, pageRequest);
                break;
            default:
                if (title == null)
                    products = productRepository.findByCategoryInAndPriceNotNullAndIsEnabledTrueAndBrand(categories, brand, pageRequest);
                else
                    products = productRepository.findByCategoryInAndTitleContainingAndPriceNotNullAndIsEnabledTrueAndBrandContaining(categories, title, brand, pageRequest);
        }

        return products.map(ProductResponse::new);
    }
}
