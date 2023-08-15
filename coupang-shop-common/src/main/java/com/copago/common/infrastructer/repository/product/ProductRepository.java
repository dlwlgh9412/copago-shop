package com.copago.common.infrastructer.repository.product;

import com.copago.common.entity.product.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Boolean existsByCoupangIdAndItemIdAndVendorId(String coupangId, String itemId, String vendorId);

    Optional<ProductEntity> findByCoupangIdAndItemIdAndVendorId(String coupangId, String itemId, String vendorId);

    List<ProductEntity> findAllByIsEnabledTrue();

    List<ProductEntity> findAllByTelegramTrue();

    List<ProductEntity> findAllByCategoryInAndPriceNotNullAndIsEnabledTrue(List<String> categories);

    Page<ProductEntity> findAllByCategoryInAndPriceNotNullAndIsEnabledTrue(List<String> categories, Pageable pageable);

    Page<ProductEntity> findByCategoryInAndPriceNotNullAndIsEnabledTrueAndBrand(List<String> categories, String brand, Pageable pageable);

    Page<ProductEntity> findByCategoryInAndTitleContainingAndPriceNotNullAndIsEnabledTrue(List<String> categories, String title, Pageable pageable);

    Page<ProductEntity> findByCategoryInAndTitleContainingAndPriceNotNullAndIsEnabledTrueAndBrandContaining(List<String> categories, String title, String brand, Pageable pageable);

    Page<ProductEntity> findByTitleContaining(String title, Pageable pageable);

    Page<ProductEntity> findByCategoryInAndTitleContainingAndPriceNotNullAndBrandContaining(List<String> categories, String title, String brand, Pageable pageable);

    Page<ProductEntity> findByCategoryInAndPriceNotNullAndBrand(List<String> categories, String brand, Pageable pageable);
}
