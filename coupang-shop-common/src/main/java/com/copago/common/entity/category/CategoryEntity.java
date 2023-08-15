package com.copago.common.entity.category;

import com.copago.common.entity.AbstractBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "tb_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryEntity extends AbstractBaseEntity {
    @Id
    private String category;

    @Column(name = "category_name")
    private String name;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "depth")
    private Long depth;

    @Column(name = "is_enabled")
    private Boolean isEnabled;
}
