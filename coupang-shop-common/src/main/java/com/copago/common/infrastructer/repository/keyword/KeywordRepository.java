package com.copago.common.infrastructer.repository.keyword;

import com.copago.common.entity.keyword.KeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<KeywordEntity, Long> {
    List<KeywordEntity> findByIsEnabledTrue();
}
