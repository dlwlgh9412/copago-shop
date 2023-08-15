package com.copago.common.infrastructer.repository.brand;

import com.copago.common.entity.brand.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<BrandEntity, String> {
    List<BrandEntity> findByCategoriesContaining(String category);
}
