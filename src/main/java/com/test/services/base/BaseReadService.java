package com.test.services.base;

import com.test.entities.BaseEntity;

public interface BaseReadService<T extends BaseEntity> {
    T get(Long id);
}
