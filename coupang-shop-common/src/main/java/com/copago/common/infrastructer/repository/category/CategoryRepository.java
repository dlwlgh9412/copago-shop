package com.copago.common.infrastructer.repository.category;

import com.copago.common.entity.category.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    List<CategoryEntity> findByCategoryStartingWithAndIsEnabledTrue(String category);

    List<CategoryEntity> findByIsEnabledTrue();

    List<CategoryEntity> findByCategoryInAndIsEnabledTrue(List<String> categoryList);
}
