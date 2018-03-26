package com.test.services.base.impl;

import com.test.entities.BaseEntity;
import com.test.services.base.BaseReadService;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class DefaultBaseReadService<T extends BaseEntity> implements BaseReadService<T> {
    private final JpaRepository<T, Long> jpaRepository;

    protected DefaultBaseReadService(JpaRepository<T, Long> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public T get(Long id) {
        return jpaRepository.findOne(id);
    }
}
