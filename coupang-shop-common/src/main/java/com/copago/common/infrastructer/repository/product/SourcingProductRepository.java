package com.copago.common.infrastructer.repository.product;

import com.copago.common.entity.product.SourcingProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SourcingProductRepository extends JpaRepository<SourcingProductEntity, Long> {
    Optional<SourcingProductEntity> findByCoupangIdAndItemIdAndVendorId(String coupangId, String itemId, String vendorId);
    Optional<SourcingProductEntity> findByCoupangIdAndItemId(String coupangId, String itemId);
    List<SourcingProductEntity> findAllByTelegramTrueAndIsUseTrue();
    List<SourcingProductEntity> findAllByTelegramTrueAndIsUseFalse();
    Page<SourcingProductEntity> findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
