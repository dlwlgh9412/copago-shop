package com.copago.common.entity.brand;

import com.copago.common.entity.AbstractBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Getter
@Entity
@Table(name = "tb_brand")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BrandEntity extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "brand_name")
    private String name;

    @Column(name = "categories")
    private String categories;

    public List<String> getCategories() {
        return Arrays.asList(categories.split(","));
    }
}
