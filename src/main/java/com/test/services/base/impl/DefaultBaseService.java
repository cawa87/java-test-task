package com.test.services.base.impl;

import com.test.common.exceptions.base.NonPersistedAccountUpdateException;
import com.test.entities.BaseEntity;
import com.test.services.base.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;

abstract public class DefaultBaseService<T extends BaseEntity> extends DefaultBaseReadService<T> implements BaseService<T> {

    private final JpaRepository<T, Long> jpaRepository;

    protected DefaultBaseService(JpaRepository<T, Long> jpaRepository) {
        super(jpaRepository);
        this.jpaRepository = jpaRepository;
    }

    @Override
    public T create(T entity) {
        entity.setId(null);
        return jpaRepository.save(entity);
    }

    @Override
    public T update(T entity) {
        if (null == entity.getId()) {
            throw new NonPersistedAccountUpdateException();
        }
        return jpaRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.delete(id);
    }
}
