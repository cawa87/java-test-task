package com.test.services.base;

import com.test.entities.BaseEntity;

public interface BaseService<T extends BaseEntity> extends BaseReadService<T> {

    T create(T entity);

    T update(T entity);

    void delete(Long id);
}
