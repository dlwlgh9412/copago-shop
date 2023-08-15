package com.copago.common.infrastructer.repository.alarm;

import com.copago.common.entity.alarm.AlarmProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlarmProductRepository extends JpaRepository<AlarmProductEntity, Long> {
    Boolean existsByCoupangIdAndItemIdAndVendorId(String coupangId, String itemId, String vendorId);

    Optional<AlarmProductEntity> findByCoupangIdAndItemIdAndVendorId(String coupangId, String itemId, String vendorId);

    List<AlarmProductEntity> findAllByIsNewTrue();
    List<AlarmProductEntity> findAllByIsNewFalse();
}