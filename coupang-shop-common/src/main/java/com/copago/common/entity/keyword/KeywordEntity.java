package com.copago.common.entity.keyword;

import com.copago.common.entity.AbstractBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "tb_keyword")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeywordEntity extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "min_price")
    private Long minPrice;

    @Column(name = "max_price")
    private Long maxPrice;

    @Column(name = "condition")
    private String condition;

    @Column(name = "sale")
    private Long sale;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "chat_id")
    private String chatId;

    @Column(name = "category")
    private String category;


}
