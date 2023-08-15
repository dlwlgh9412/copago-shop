package com.copago.common.infrastructer.repository.alarm;

import com.copago.common.entity.alarm.AlarmProductMappingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlarmProductMappingRepository extends JpaRepository<AlarmProductMappingEntity, Long> {
    List<AlarmProductMappingEntity> findByPidAndPriceIsGreaterThanEqual(Long pid, Long price);

    Boolean existsByUidAndPid(Long uid, Long pid);

    Optional<AlarmProductMappingEntity> findByUidAndPid(Long uid, Long pid);

    Page<AlarmProductMappingEntity> findByUid(Long uid, Pageable pageable);
}
