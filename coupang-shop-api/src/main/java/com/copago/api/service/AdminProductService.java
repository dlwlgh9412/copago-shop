package com.copago.api.service;

import com.copago.api.web.dto.request.AdminProductAddRequest;
import com.copago.api.web.dto.response.AdminProductResponse;
import com.copago.common.entity.brand.BrandEntity;
import com.copago.common.entity.category.CategoryEntity;
import com.copago.common.entity.product.ProductEntity;
import com.copago.common.infrastructer.client.coupang.CoupangClient;
import com.copago.common.infrastructer.client.coupang.dto.response.CoupangReturnMResponse;
import com.copago.common.infrastructer.repository.brand.BrandRepository;
import com.copago.common.infrastructer.repository.category.CategoryRepository;
import com.copago.common.infrastructer.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminProductService {
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final CoupangClient coupangClient;

    public Page<AdminProductResponse> getProducts(String title, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<ProductEntity> result;
        if (StringUtils.hasText(title)) result = productRepository.findAll(pageRequest);
        else result = productRepository.findByTitleContaining(title, pageRequest);

        return result.map(product -> {
            CategoryEntity category = null;
            if (StringUtils.hasText(product.getCategory()))
                category = categoryRepository.findById(product.getCategory()).orElseThrow(() -> new RuntimeException("상품의 카테고리와 일치하는 카테고리를 찾을 수 없습니다."));

            BrandEntity brand = null;
            if (StringUtils.hasText(product.getBrand()))
                brand = brandRepository.findById(product.getBrand()).orElseThrow(() -> new RuntimeException("상품의 브랜드와 일치하는 브랜드를 찾을 수 없습니다."));

            return new AdminProductResponse(product, category, brand);
        });
    }

    public AdminProductResponse getProduct(Long productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("잘못된 상품 ID 입니다."));
        CategoryEntity category = categoryRepository.findById(product.getCategory()).orElseThrow(() -> new RuntimeException("상품의 카테고리와 일치하는 카테고리를 찾을 수 없습니다."));
        BrandEntity brand = brandRepository.findById(product.getBrand()).orElseThrow(() -> new RuntimeException("상품의 브랜드와 일치하는 브랜드를 찾을 수 없습니다."));

        return new AdminProductResponse(product, category, brand);
    }

    @Transactional
    public AdminProductResponse addProduct(AdminProductAddRequest request) {
        CategoryEntity category = categoryRepository.findById(request.getCategory()).orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        BrandEntity brand = brandRepository.findById(request.getBrand()).orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다."));

        CoupangReturnMResponse.CoupangMData data = coupangClient.getProductDetail(request.getCoupangId(), request.getVendorId());

        String vendorItemId = data.getVendorItemDetail().getItem().getVendorItemId().toString();
        String title = data.getVendorItemDetail().getItem().getItemName();
        Long originPrice = data.getVendorItemDetail().getItem().getOriginalPrice();
        Long price = data.getVendorItemDetail().getItem().getCouponPrice();

        String cardInfo = Optional.ofNullable(data.getPanelContainerVo())
                .map(CoupangReturnMResponse.CoupangMPanelContainer::getPanelList)
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0).getContents())
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0).getContents())
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0).getTitle())
                .map(titleMap -> titleMap.stream()
                        .map(text -> (String) text.get("text"))
                        .filter(text -> text != null)
                        .collect(Collectors.joining()))
                .orElse(null);
        String image = data.getVendorItemDetail().getResource().getOriginalSquare().get("url").toString();

        if (productRepository.existsByCoupangIdAndItemIdAndVendorId(request.getCoupangId(), request.getItemId(), vendorItemId))
            throw new IllegalStateException("이미 등록된 상품입니다.");

        ProductEntity product = productRepository.save(
                ProductEntity.builder()
                        .title(title)
                        .category(request.getCategory())
                        .brand(request.getBrand())
                        .price(price)
                        .originPrice(originPrice)
                        .cardInfo(cardInfo)
                        .telegram(request.getIsTelegram())
                        .isEnabled(request.getEnabled())
                        .coupangId(request.getCoupangId())
                        .itemId(request.getItemId())
                        .vendorId(request.getVendorId())
                        .image(image)
                        .build());

        return new AdminProductResponse(product, category, brand);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("잘못된 상품 ID 입니다."));
        productRepository.delete(product);
    }
}
