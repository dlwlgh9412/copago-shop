package com.copago.api.web.dto.response;

import com.copago.common.entity.category.CategoryEntity;
import lombok.Getter;

@Getter
public class CategoryResponse {
    private final String id;
    private final String name;
    private final String displayName;
    private final Long depth;
    private final String parentId;

    public CategoryResponse(CategoryEntity entity) {
        this.id = entity.getCategory();
        this.name = entity.getName();
        this.displayName = entity.getDisplayName();
        this.depth = entity.getDepth();
        this.parentId = entity.getParentId();
    }
}
